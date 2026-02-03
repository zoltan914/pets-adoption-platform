package com.devtiro.pets.mappers;

import com.devtiro.pets.domain.dto.AdoptionApplicationCreateRequest;
import com.devtiro.pets.domain.dto.AdoptionApplicationDto;
import com.devtiro.pets.domain.entity.AdoptionApplication;
import com.devtiro.pets.domain.entity.Pet;
import com.devtiro.pets.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdoptionApplicationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "petId", source = "pet.id")
    @Mapping(target = "applicantId", source = "user.id")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "phoneNumber", source = "user.phoneNumber")
    @Mapping(target = "alternatePhone", source = "request.alternatePhone")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "address", source = "request.address")
    @Mapping(target = "status", ignore = true)
    AdoptionApplication toAdoptionApplication(AdoptionApplicationCreateRequest request, Pet pet, User user);

    @Mapping(target = "id", source = "application.id")
    @Mapping(target = "petName", source = "pet.name")
    @Mapping(target = "applicantUsername", source = "user.username")
    @Mapping(target = "firstName", source = "application.firstName")
    @Mapping(target = "lastName", source = "application.lastName")
    @Mapping(target = "phoneNumber", source = "application.phoneNumber")
    @Mapping(target = "alternatePhone", source = "application.alternatePhone")
    @Mapping(target = "email", source = "application.email")
    @Mapping(target = "address", source = "application.address")
    @Mapping(target = "status", source = "application.status")
    @Mapping(target = "createdBy", source = "application.createdBy")
    @Mapping(target = "createdAt", source = "application.createdAt")
    @Mapping(target = "updatedAt", source = "application.updatedAt")
    AdoptionApplicationDto toAdoptionApplicationDto(AdoptionApplication application, Pet pet, User user);

}
