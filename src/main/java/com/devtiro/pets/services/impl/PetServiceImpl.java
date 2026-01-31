package com.devtiro.pets.services.impl;

import com.devtiro.pets.domain.dto.PetCreateRequest;
import com.devtiro.pets.domain.dto.PetDto;
import com.devtiro.pets.domain.dto.PhotoDto;
import com.devtiro.pets.domain.dto.PhotoUploadRequest;
import com.devtiro.pets.domain.entity.Pet;
import com.devtiro.pets.domain.entity.PetStatus;
import com.devtiro.pets.domain.entity.Photo;
import com.devtiro.pets.domain.entity.User;
import com.devtiro.pets.exceptions.PetNotFoundException;
import com.devtiro.pets.mappers.PetMapper;
import com.devtiro.pets.mappers.PhotoMapper;
import com.devtiro.pets.repositories.PetRepository;
import com.devtiro.pets.repositories.PhotoRepository;
import com.devtiro.pets.services.PetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

}
