package com.devtiro.pets.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordUpdateRequest {

    private String type;
    private String description;
    private String veterinarian;
    private String notes;
    private LocalDateTime appointmentDate;

}
