package com.devtiro.pets.domain.dto;

import com.devtiro.pets.domain.entity.PetStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetStatusUpdateRequest {

    private PetStatus status;

}
