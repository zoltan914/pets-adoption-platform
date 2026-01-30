package com.devtiro.pets.mappers;

import com.devtiro.pets.domain.dto.MedicalRecordDto;
import com.devtiro.pets.domain.entity.MedicalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicalRecordMapper {

    MedicalRecordDto toMedicalRecordDto(MedicalRecord medicalRecord);

}
