package com.devtiro.pets.services;

import com.devtiro.pets.domain.dto.MedicalRecordDto;
import com.devtiro.pets.domain.dto.MedicalRecordUpdateRequest;
import com.devtiro.pets.domain.entity.User;

public interface MedicalRecordService {

    MedicalRecordDto addMedicalRecord(String petId, MedicalRecordUpdateRequest medicalRecordDto, User staff);

}
