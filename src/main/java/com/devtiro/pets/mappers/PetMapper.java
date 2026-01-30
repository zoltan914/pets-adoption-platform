package com.devtiro.pets.mappers;

import com.devtiro.pets.domain.dto.PetResponse;
import com.devtiro.pets.domain.entity.Pet;
import com.devtiro.pets.repositories.PetRepository;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {PhotoMapper.class, MedicalRecordMapper.class})
public interface PetMapper {

    PetResponse toPetResponse(Pet pet);

}
