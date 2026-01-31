package com.devtiro.pets.services;

import com.devtiro.pets.domain.dto.*;
import com.devtiro.pets.domain.entity.PetStatus;
import com.devtiro.pets.domain.entity.User;

import java.util.List;

public interface PetService {

    List<PetDto> getAllPets();

    PetDto createPet(PetCreateRequest request, User staff);

    PetDto updatePet(String petId, PetUpdateRequest request);

    PetDto updatePetStatus(String petId, PetStatusUpdateRequest request);

    void deletePet(String petId);

}
