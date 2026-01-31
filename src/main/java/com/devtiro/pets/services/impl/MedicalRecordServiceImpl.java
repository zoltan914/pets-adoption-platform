package com.devtiro.pets.services.impl;

import com.devtiro.pets.domain.dto.MedicalRecordDto;
import com.devtiro.pets.domain.dto.MedicalRecordUpdateRequest;
import com.devtiro.pets.domain.entity.MedicalRecord;
import com.devtiro.pets.domain.entity.Pet;
import com.devtiro.pets.domain.entity.User;
import com.devtiro.pets.exceptions.MedicalRecordNotFoundException;
import com.devtiro.pets.exceptions.PetNotFoundException;
import com.devtiro.pets.mappers.MedicalRecordMapper;
import com.devtiro.pets.repositories.MedicalRecordRepository;
import com.devtiro.pets.repositories.PetRepository;
import com.devtiro.pets.services.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicalRecordMapper medicalRecordMapper;

    @Override
    public List<MedicalRecordDto> getAllMedicalRecordsByPetId(String petId) {
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAllByPetId(petId);

        return medicalRecords.stream()
                .map(medicalRecordMapper::toMedicalRecordDto)
                .toList();
    }

    @Override
    public MedicalRecordDto addMedicalRecord(String petId, MedicalRecordUpdateRequest request, User staff) {

        MedicalRecord medicalRecord = medicalRecordMapper.toMedicalRecord(request);
        medicalRecord.setPetId(petId);
        medicalRecord.setStaffId(staff.getId());

        MedicalRecord savedMedicalRecord = medicalRecordRepository.save(medicalRecord);
        log.info("Medical Record added with id: " + savedMedicalRecord.getId());
        return medicalRecordMapper.toMedicalRecordDto(savedMedicalRecord);
    }

    @Override
    public MedicalRecordDto updateMedicalRecord(String medicalRecordId, MedicalRecordUpdateRequest request) {
        MedicalRecord existingMedicalRecord = medicalRecordRepository.findById(medicalRecordId)
                .orElseThrow(() -> new MedicalRecordNotFoundException("Medical record not found with id: " + medicalRecordId));

        medicalRecordMapper.updateMedicalRecord(existingMedicalRecord, request);

        MedicalRecord updatedMedicalRecord = medicalRecordRepository.save(existingMedicalRecord);
        log.info("Medical Record updated with id: " + updatedMedicalRecord.getId());
        return medicalRecordMapper.toMedicalRecordDto(updatedMedicalRecord);
    }
}
