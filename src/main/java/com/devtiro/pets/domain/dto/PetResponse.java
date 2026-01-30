package com.devtiro.pets.domain.dto;

import com.devtiro.pets.domain.entity.PetSize;
import com.devtiro.pets.domain.entity.Species;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetResponse {

    private String id;
    private String name;
    private String description;
    private Species species;
    private Integer age;
    private PetSize petSize;
    private AddressDto address;
    private GeoPointDto location;
    private List<PhotoDto> photos = new ArrayList<>();
    private List<MedicalRecordDto> medicalRecords = new ArrayList<>();

    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private LocalDateTime updatedAt;

}
