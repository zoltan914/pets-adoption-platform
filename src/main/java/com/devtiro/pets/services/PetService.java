package com.devtiro.pets.services;

import com.devtiro.pets.domain.dto.*;
import com.devtiro.pets.domain.entity.Pet;
import com.devtiro.pets.domain.entity.PetStatus;
import com.devtiro.pets.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface PetService {

    List<PetDto> getAllPets();

    Page<PetDto> getAllAvailablePets(Pageable pageable);

    PetDto createPet(PetCreateRequest request, User staff);

    PetDto updatePet(String petId, PetUpdateRequest request);

    PetDto updatePetStatus(String petId, PetStatusUpdateRequest request);

    void deletePet(String petId);

    Page<PetDto> searchPets(PetSearchRequest request, Pageable pageable);

}
