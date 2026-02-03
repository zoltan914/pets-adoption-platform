package com.devtiro.pets.domain.dto;

import com.devtiro.pets.domain.entity.AdoptionApplicationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionApplicationDto {

    private String id;
    private String petId;
    private String petName;
    private String applicantId;
    private String applicantUsername;
    
    // Personal Contact Information
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String alternatePhone;
    private String email;
    private AddressDto address;

    // Living Situation Details
    private LivingSituationDto livingSituation;

    // Additional Information
    private String adoptionReason;
    private String petExperience;
    private String veterinarianName;
    private String veterinarianContact;
    private String references;
    private String additionalComments;

    // Application Status and Metadata
    private AdoptionApplicationStatus status;
    private String staffNotes;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime submittedAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private String createdBy;
    private String reviewedBy;


}
