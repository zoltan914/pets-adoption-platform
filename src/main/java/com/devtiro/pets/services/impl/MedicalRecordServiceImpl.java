package com.devtiro.pets.services.impl;

import com.devtiro.pets.domain.dto.MedicalRecordDto;
import com.devtiro.pets.domain.dto.MedicalRecordUpdateRequest;
import com.devtiro.pets.domain.entity.MedicalRecord;
import com.devtiro.pets.domain.entity.User;
import com.devtiro.pets.mappers.MedicalRecordMapper;
import com.devtiro.pets.repositories.MedicalRecordRepository;
import com.devtiro.pets.services.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicalRecordMapper medicalRecordMapper;

    @Override
    public MedicalRecordDto addMedicalRecord(String petId, MedicalRecordUpdateRequest request, User staff) {

        MedicalRecord medicalRecord = medicalRecordMapper.toMedicalRecord(request);
        medicalRecord.setPetId(petId);
        medicalRecord.setStaffId(staff.getId());

        MedicalRecord savedMedicalRecord = medicalRecordRepository.save(medicalRecord);
        log.info("Medical Record saved with id: " + savedMedicalRecord.getId());
        return medicalRecordMapper.toMedicalRecordDto(savedMedicalRecord);
    }
}
