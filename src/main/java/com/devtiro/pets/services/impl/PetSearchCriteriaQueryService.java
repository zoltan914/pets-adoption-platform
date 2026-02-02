package com.devtiro.pets.services.impl;

import com.devtiro.pets.domain.dto.PetDto;
import com.devtiro.pets.domain.dto.PetSearchRequest;
import com.devtiro.pets.domain.entity.Pet;
import com.devtiro.pets.domain.entity.PetStatus;
import com.devtiro.pets.mappers.PetMapper;
import com.devtiro.pets.services.PetSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.GeoDistanceOrder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service("criteria")
@RequiredArgsConstructor
public class PetSearchCriteriaQueryService implements PetSearchService {

    private final PetMapper petMapper;
    private final ElasticsearchOperations elasticsearchOperations;

    public Page<PetDto> searchPets(PetSearchRequest request, Pageable pageable) {
        // Build the search criteria
        Criteria elasticCriteria = buildSearchCriteria(request);

        // Build the query with sorting and pagination
        Query query = buildSorting(elasticCriteria, request, pageable);

        // Set pageable without sort, it was handled right before this
        Pageable pageableWithoutSort = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize()
        );
        query.setPageable(pageableWithoutSort);

        SearchHits<Pet> searchHits = elasticsearchOperations.search(query, Pet.class);

        // Convert results to DTOs and calculate distances if geo search
        List<PetDto> petDtos = searchHits.getSearchHits().stream()
                .map(hit -> {
                    Pet pet = hit.getContent();
                    PetDto dto = petMapper.toPetDto(pet);

                    // Calculate and set distance if this is a geolocation search
                    if (hasLocation(request)) {
                        double distance = calculateDistance(
                                request.getLocation().getLat(),
                                request.getLocation().getLon(),
                                pet.getLocation().getLat(),
                                pet.getLocation().getLon()
                        );
                        dto.setDistance(Math.round(distance * 100.0) / 100.0); // Round to 2 decimal places
                    }
                    return dto;
                })
                .toList();

        long totalHits = searchHits.getTotalHits();
        return new PageImpl<>(petDtos, pageable, totalHits);
    }

    private Criteria buildSearchCriteria(PetSearchRequest criteria) {
        List<Criteria> criteriaList = new ArrayList<>();

        // Species filter
        if (criteria.getSpecies() != null) {
            criteriaList.add(Criteria.where("species").is(criteria.getSpecies()));
        }

        // Pet Size filter
        if (criteria.getPetSize() != null) {
            criteriaList.add(Criteria.where("petSize").is(criteria.getPetSize()));
        }

        // Age filter
        if (criteria.getMinAge() != null) {
            criteriaList.add(Criteria.where("age").greaterThanEqual(criteria.getMinAge()));
        }
        if (criteria.getMaxAge() != null) {
            criteriaList.add(Criteria.where("age").lessThanEqual(criteria.getMaxAge()));
        }

        // Geolocation filter - radius search
        if (criteria.getLocation() != null && criteria.getLocation().getLat() != null
                && criteria.getLocation().getLon() != null && criteria.getDistance() != null) {

            Point point = new Point(criteria.getLocation().getLon(), criteria.getLocation().getLat());
            Distance distance = new Distance(criteria.getDistance(), Metrics.KILOMETERS);

            criteriaList.add(Criteria.where("location").within(point, distance));

            log.info("Geo search: center ({}, {}), radius {} km",
                    criteria.getLocation().getLat(), criteria.getLocation().getLon(), criteria.getDistance());
        }

        // Only show available pets
        criteriaList.add(Criteria.where("status").is(PetStatus.AVAILABLE));

        // Combine the criterias
        Criteria combinedCriteria = criteriaList.getFirst();
        for (int i = 1; i < criteriaList.size(); i++) {
            combinedCriteria = combinedCriteria.and(criteriaList.get(i));
        }

        return combinedCriteria;
    }

    private Query buildSorting(Criteria criteria, PetSearchRequest request, Pageable pageable) {
        CriteriaQuery query = new CriteriaQuery(criteria);
        Optional<Sort.Order> explicitGeoOrder = pageable.getSort().stream()
                .filter(order -> order.getProperty().equalsIgnoreCase("distance")
                        || order.getProperty().equalsIgnoreCase("closest"))
                .findFirst();

        // Regular field sorting
        for (Sort.Order order : pageable.getSort()) {
            String prop = order.getProperty();
            Sort.Direction direction = order.getDirection();

            if (prop.equalsIgnoreCase("distance") || prop.equalsIgnoreCase("closest")) continue;

            Sort fieldSort = Sort.by(direction, prop);
            query.addSort(fieldSort);
        }
        // Geo distance sorting
        if (hasLocation(request)) {
            // If user provided "distance,desc", use DESC. Otherwise, default to ASC.
            Sort.Direction direction = explicitGeoOrder
                    .map(Sort.Order::getDirection)
                    .orElse(Sort.Direction.ASC);
            Sort geoSort = buildGeoSort(request, direction);
            query.addSort(geoSort);
        }

        return query;
    };

    private Sort buildGeoSort(PetSearchRequest req, Sort.Direction dir) {
        GeoPoint geoPoint = new GeoPoint(req.getLocation().getLat(), req.getLocation().getLon());
        GeoDistanceOrder geoDistanceOrder = new GeoDistanceOrder("location", geoPoint);
        return Sort.by(geoDistanceOrder);
    }


    private boolean hasLocation(PetSearchRequest r) {
        return r.getLocation() != null && r.getLocation().getLat() != null && r.getLocation().getLon() != null;
    }

    /**
     * Calculate distance between two points using Haversine formula
     * @param lat1 Latitude of point 1
     * @param lon1 Longitude of point 1
     * @param lat2 Latitude of point 2
     * @param lon2 Longitude of point 2
     * @return Distance in kilometers
     */
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS_KM = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
   }

}
