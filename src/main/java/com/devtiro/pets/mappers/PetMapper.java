package com.devtiro.pets.mappers;

import com.devtiro.pets.domain.dto.PetCreateRequest;
import com.devtiro.pets.domain.dto.PetDto;
import com.devtiro.pets.domain.dto.PetUpdateRequest;
import com.devtiro.pets.domain.entity.Pet;
import com.devtiro.pets.domain.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {PhotoMapper.class, MedicalRecordMapper.class, GeoLocationMapper.class})
public interface PetMapper {

    PetDto toPetDto(Pet pet);

    @Mapping(target = "staffId", source = "staff.id")
    @Mapping(target = "staffName", expression = "java(staff.getFirstName() + \" \" + staff.getLastName())")
    @Mapping(target = "staffEmail", source = "staff.email")
    Pet toPet(PetCreateRequest request, User staff);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePet(@MappingTarget Pet pet, PetUpdateRequest request);

}
