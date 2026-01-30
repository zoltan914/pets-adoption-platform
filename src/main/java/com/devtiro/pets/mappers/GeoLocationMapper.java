package com.devtiro.pets.mappers;

import com.devtiro.pets.domain.dto.GeoPointDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GeoLocationMapper {

    default GeoPoint toGeoPoint(GeoPointDto location) {
        if (location == null) {
            return null;
        }
        return new GeoPoint(location.getLat(), location.getLon());
    }

    default GeoPointDto toGeoLocationDto(GeoPoint location) {
        if (location == null) {
            return null;
        }
        GeoPointDto dto = new GeoPointDto();
        dto.setLat(location.getLat());
        dto.setLon(location.getLon());
        return dto;
    }
}