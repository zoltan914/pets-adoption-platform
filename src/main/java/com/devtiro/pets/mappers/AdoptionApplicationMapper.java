package com.devtiro.pets.mappers;

import com.devtiro.pets.domain.dto.AdoptionApplicationCreateRequest;
import com.devtiro.pets.domain.dto.AdoptionApplicationDto;
import com.devtiro.pets.domain.dto.AdoptionApplicationUpdateRequest;
import com.devtiro.pets.domain.entity.AdoptionApplication;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdoptionApplicationMapper {


    AdoptionApplication toEntity(AdoptionApplicationCreateRequest request);

    @Mapping(target = "petName", source = "application.pet.name")
    @Mapping(target = "applicantUsername", source = "application.applicant.username")
    AdoptionApplicationDto toAdoptionApplicationDto(AdoptionApplication application);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget AdoptionApplication application, AdoptionApplicationUpdateRequest request);

}
