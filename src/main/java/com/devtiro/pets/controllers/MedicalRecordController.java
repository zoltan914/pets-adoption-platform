package com.devtiro.pets.controllers;

import com.devtiro.pets.domain.dto.MedicalRecordDto;
import com.devtiro.pets.domain.dto.MedicalRecordUpdateRequest;
import com.devtiro.pets.domain.entity.User;
import com.devtiro.pets.services.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping("/{petId}")
    public List<MedicalRecordDto> getAllMedicalRecordsByPetId(
            @PathVariable String petId
    ) {
        return medicalRecordService.getAllMedicalRecordsByPetId(petId);
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping("/{petId}")
    public MedicalRecordDto addMedicalRecord(
            @PathVariable String petId,
            @Valid @RequestBody MedicalRecordUpdateRequest request,
            @AuthenticationPrincipal User staff
    ) {
        return  medicalRecordService.addMedicalRecord(petId, request, staff);
    }

    @PreAuthorize("hasRole('STAFF')")
    @PutMapping("/{medicalRecordId}")
    public MedicalRecordDto updateMedicalRecord(
            @PathVariable String medicalRecordId,
            @Valid @RequestBody MedicalRecordUpdateRequest request
    ) {
        return  medicalRecordService.updateMedicalRecord(medicalRecordId, request);
    }

}
