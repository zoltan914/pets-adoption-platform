package com.devtiro.pets.services;

import com.devtiro.pets.domain.dto.MedicalRecordDto;
import com.devtiro.pets.domain.dto.MedicalRecordUpdateRequest;
import com.devtiro.pets.domain.entity.User;

import java.util.List;

public interface MedicalRecordService {

    List<MedicalRecordDto> getAllMedicalRecordsByPetId(String petId);

    MedicalRecordDto addMedicalRecord(String petId, MedicalRecordUpdateRequest request, User staff);

    MedicalRecordDto updateMedicalRecord(String medicalRecordId, MedicalRecordUpdateRequest request);

}
