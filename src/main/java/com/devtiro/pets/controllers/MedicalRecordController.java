package com.devtiro.pets.controllers;

import com.devtiro.pets.domain.dto.MedicalRecordDto;
import com.devtiro.pets.domain.dto.MedicalRecordUpdateRequest;
import com.devtiro.pets.domain.entity.User;
import com.devtiro.pets.services.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @PostMapping("/{petId}")
    public MedicalRecordDto addMedicalRecord(
            @PathVariable String petId,
            @Valid @RequestBody MedicalRecordUpdateRequest request,
            @AuthenticationPrincipal User staff
    ) {
        return  medicalRecordService.addMedicalRecord(petId, request, staff);
    }

}
