package com.devtiro.pets.domain.dto;

import com.devtiro.pets.domain.entity.AdoptionApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionApplicationUpdateStatusRequest {

    @NotNull(message = "Status is required")
    private AdoptionApplicationStatus status;

    private String staffNotes;
}
