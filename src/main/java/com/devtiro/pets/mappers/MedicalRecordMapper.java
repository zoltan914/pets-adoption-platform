package com.devtiro.pets.mappers;

import com.devtiro.pets.domain.dto.MedicalRecordDto;
import com.devtiro.pets.domain.dto.MedicalRecordUpdateRequest;
import com.devtiro.pets.domain.entity.MedicalRecord;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicalRecordMapper {

    MedicalRecord toMedicalRecord(MedicalRecordUpdateRequest request);

    MedicalRecordDto toMedicalRecordDto(MedicalRecord medicalRecord);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMedicalRecord(@MappingTarget MedicalRecord medicalRecord, MedicalRecordUpdateRequest request);

}
