package com.devtiro.pets.domain.dto;

import com.devtiro.pets.domain.entity.PetSize;
import com.devtiro.pets.domain.entity.PetStatus;
import com.devtiro.pets.domain.entity.Species;
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
public class PetDto {

    private String id;
    private String name;
    private String description;
    private Species species;
    private Integer age;
    private PetSize petSize;
    private PetStatus status;
    private AddressDto address;
    private GeoPointDto location;
    private Double distance;

    private String createdBy;
    private String updatedBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

}
