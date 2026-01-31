package com.devtiro.pets.services.impl;

import com.devtiro.pets.domain.dto.PetCreateRequest;
import com.devtiro.pets.domain.dto.PetDto;
import com.devtiro.pets.domain.dto.PetUpdateRequest;
import com.devtiro.pets.domain.entity.Pet;
import com.devtiro.pets.domain.entity.PetStatus;
import com.devtiro.pets.domain.entity.User;
import com.devtiro.pets.exceptions.PetNotFoundException;
import com.devtiro.pets.mappers.PetMapper;
import com.devtiro.pets.repositories.PetRepository;
import com.devtiro.pets.services.PetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;

    @Override
    public PetDto createPet(PetCreateRequest request, User staff) {

        Pet pet = petMapper.toPet(request, staff);
        pet.setStatus(PetStatus.AVAILABLE);

        Pet savedPet = petRepository.save(pet);
        return petMapper.toPetDto(savedPet);
    }

    @Override
    public PetDto updatePet(String petId, PetUpdateRequest request) {
        Pet existingPet = petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException("Pet not found with id: " + petId));

        petMapper.updatePet(existingPet, request);
        Pet updatedPet = petRepository.save(existingPet);

        return petMapper.toPetDto(updatedPet);
    }

}
