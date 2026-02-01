package com.devtiro.pets.services.impl;

import com.devtiro.pets.domain.dto.*;
import com.devtiro.pets.domain.entity.Pet;
import com.devtiro.pets.domain.entity.PetStatus;
import com.devtiro.pets.domain.entity.User;
import com.devtiro.pets.exceptions.PetNotFoundException;
import com.devtiro.pets.mappers.PetMapper;
import com.devtiro.pets.repositories.PetRepository;
import com.devtiro.pets.services.PetSearchService;
import com.devtiro.pets.services.PetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;
    private final PetSearchService petSearchService;

    @Override
    public List<PetDto> getAllPets() {
        List<Pet> pets = petRepository.findAll();
        return pets.stream()
                .map(petMapper::toPetDto)
                .toList();
    }

    @Override
    public Page<PetDto> getAllAvailablePets(Pageable pageable) {
        Page<Pet> pets = petRepository.findAllByStatus(PetStatus.AVAILABLE, pageable);
        return pets.map(petMapper::toPetDto);
    }

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

    @Override
    public PetDto updatePetStatus(String petId, PetStatusUpdateRequest request) {
        Pet existingPet = petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException("Pet not found with id: " + petId));

        if (existingPet.getStatus().equals(request.getStatus())) {
            throw new IllegalArgumentException("Pet is already in this status: " + request.getStatus());
        }

        existingPet.setStatus(request.getStatus());

        Pet updatedPet = petRepository.save(existingPet);
        log.info("Pet status updated to: {}", updatedPet.getStatus());
        return petMapper.toPetDto(updatedPet);
    }

    @Override
    public void deletePet(String petId) {
        Pet existingPet = petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException("Pet not found with id: " + petId));

        log.info("Pet to deleted with id: {}", existingPet.getId());
        petRepository.delete(existingPet);
    }

    @Override
    public Page<PetDto> searchPets(PetSearchRequest request, Pageable pageable) {
        return petSearchService.searchPets(request, pageable);
    }


}