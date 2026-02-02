package com.devtiro.pets.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionApplicationCreateRequest {

    @NotBlank(message = "Pet ID is required")
    private String petId;

    // Personal Contact Information
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private String alternatePhone;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Address is required")
    @Valid
    private AddressDto address;

    // Living Situation Details
    @NotNull(message = "Living situation details are required")
    @Valid
    private LivingSituationDto livingSituation;

    // Additional Information
    @NotBlank(message = "Please provide a reason for adoption")
    private String adoptionReason;

    private String petExperience;

    private String veterinarianName;

    private String veterinarianContact;

    private String references;

    private String additionalComments;

    // Flag to indicate if this should be saved as draft or submitted
    private Boolean isDraft;
}
