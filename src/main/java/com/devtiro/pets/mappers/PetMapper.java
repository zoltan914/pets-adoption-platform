package com.devtiro.pets.mappers;

import com.devtiro.pets.domain.dto.PetCreateRequest;
import com.devtiro.pets.domain.dto.PetDto;
import com.devtiro.pets.domain.entity.Pet;
import com.devtiro.pets.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {PhotoMapper.class, MedicalRecordMapper.class, GeoLocationMapper.class})
public interface PetMapper {

    PetDto toPetDto(Pet pet);

    // TODO these mappings should be in the service
    @Mapping(target = "staffId", source = "staff.id")
    @Mapping(target = "staffName", expression = "java(staff.getFirstName() + \" \" + staff.getLastName())")
    @Mapping(target = "staffEmail", source = "staff.email")
    Pet toPet(PetCreateRequest request, User staff);

}
