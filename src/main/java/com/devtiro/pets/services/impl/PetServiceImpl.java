package com.devtiro.pets.services.impl;

import com.devtiro.pets.domain.dto.PetCreateRequest;
import com.devtiro.pets.domain.dto.PetResponse;
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
    public PetResponse createPet(PetCreateRequest request) {

    }


}
