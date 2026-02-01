package com.devtiro.pets.services;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.DistanceUnit;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.GeoDistanceSort;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.devtiro.pets.domain.dto.PetDto;
import com.devtiro.pets.domain.dto.PetSearchRequest;
import com.devtiro.pets.domain.entity.Pet;
import com.devtiro.pets.domain.entity.PetStatus;
import com.devtiro.pets.mappers.PetMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service("native")
@RequiredArgsConstructor
public class PetSearchNativeElasticClientService implements PetSearchService {

    private final PetMapper petMapper;
    private final ElasticsearchClient elasticsearchClient;
    private static final String petIndex = "pets";

    @Override
    public Page<PetDto> searchPets(PetSearchRequest request, Pageable pageable) {
        // Build the base request
        SearchRequest.Builder searchRequestBuilder = new SearchRequest.Builder()
                .index(petIndex)
                .from((int) pageable.getOffset())
                .size(pageable.getPageSize());

        // Apply Query Filters
        searchRequestBuilder.query(q -> q.bool(buildBoolQuery(request).build()));

        // Apply Sorting & Ensure Distance Calculation
        applySorting(searchRequestBuilder, request, pageable);

        try {
            SearchResponse<Pet> response = elasticsearchClient.search(searchRequestBuilder.build(), Pet.class);

            List<PetDto> petDtos = response.hits().hits().stream()
                    .map(hit -> mapHitToDto(hit, request))
                    .toList();

            long totalHits = response.hits().total() != null ? response.hits().total().value() : 0;
            return new PageImpl<>(petDtos, pageable, totalHits);

        } catch (IOException e) {
            log.error("Elasticsearch search failed", e);
            throw new RuntimeException("Error searching pets", e);
        }
    }

    private BoolQuery.Builder buildBoolQuery(PetSearchRequest request) {
        BoolQuery.Builder bool = new BoolQuery.Builder();

        // Standard Filters
        if (request.getSpecies() != null)
            bool.must(m -> m.term(t -> t.field("species").value(request.getSpecies().name())));
        if (request.getPetSize() != null)
            bool.must(m -> m.term(t -> t.field("petSize").value(request.getPetSize().name())));

        // Age Range - Fixed Range Query Syntax
        if (request.getMinAge() != null || request.getMaxAge() != null) {
            bool.must(m -> m.range(r -> {
                if (request.getMinAge() != null) r.term(f -> f.field("age").gte(JsonData.of(request.getMinAge()).toString()));
                if (request.getMaxAge() != null) r.term(f -> f.field("age").lte(JsonData.of(request.getMaxAge()).toString()));
                return r;
            }));
        }

        // Geo Filter
        if (hasLocation(request) && request.getDistance() != null) {
            bool.filter(f -> f.geoDistance(g -> g
                    .field("location")
                    .distance(request.getDistance() + "km")
                    .location(l -> l.latlon(ll -> ll
                            .lat(request.getLocation().getLat())
                            .lon(request.getLocation().getLon())))
            ));
        }

        // Availability
        bool.must(m -> m.term(t -> t.field("status").value(PetStatus.AVAILABLE.name())));

        return bool;
    }

    private void applySorting(SearchRequest.Builder builder, PetSearchRequest request, Pageable pageable) {
        // 1. Check if the user explicitly provided a distance/closest sort
        Optional<Sort.Order> explicitGeoOrder = pageable.getSort().stream()
                .filter(order -> order.getProperty().equalsIgnoreCase("distance")
                        || order.getProperty().equalsIgnoreCase("closest"))
                .findFirst();

        // 2. Add all NON-geo sorts first
        for (Sort.Order order : pageable.getSort()) {
            String prop = order.getProperty();
            if (prop.equalsIgnoreCase("distance") || prop.equalsIgnoreCase("closest")) continue;

            builder.sort(s -> s.field(f -> f
                    .field(prop)
                    .order(order.isAscending() ? SortOrder.Asc : SortOrder.Desc)
            ));
        }

        // 3. ALWAYS add Distance as the final sort (or primary if requested)
        // This serves as the tie-breaker and guarantees distance values in hit.sort()
        // Add the Geo Sort
        if (hasLocation(request)) {
            // If user provided "distance,desc", use DESC. Otherwise, default to ASC.
            Sort.Direction direction = explicitGeoOrder
                    .map(Sort.Order::getDirection)
                    .orElse(Sort.Direction.ASC);

            builder.sort(s -> s.geoDistance(buildGeoSort(request, direction)));
        }
    }

    private GeoDistanceSort buildGeoSort(PetSearchRequest req, Sort.Direction dir) {
        return new GeoDistanceSort.Builder()
                .field("location")
                .location(l -> l.latlon(ll -> ll.lat(req.getLocation().getLat()).lon(req.getLocation().getLon())))
                .order(dir == Sort.Direction.DESC ? SortOrder.Desc : SortOrder.Asc)
                .unit(DistanceUnit.Kilometers)
                .build();
    }

    private PetDto mapHitToDto(Hit<Pet> hit, PetSearchRequest request) {
        PetDto dto = petMapper.toPetDto(hit.source());
        if (hit.source() != null) dto.setId(hit.id());

        if (hit.sort() != null && !hit.sort().isEmpty() && hasLocation(request)) {
            // The distance is the LAST sort value because we added it last in applySorting
            FieldValue distanceValue = hit.sort().getLast();
            dto.setDistance(distanceValue.doubleValue());
        }
        return dto;
    }

    private boolean hasLocation(PetSearchRequest r) {
        return r.getLocation() != null && r.getLocation().getLat() != null && r.getLocation().getLon() != null;
    }

}
