package com.devtiro.pets.services;

import com.devtiro.pets.domain.dto.PetCreateRequest;
import com.devtiro.pets.domain.dto.PetResponse;

public interface PetService {

    PetResponse createPet(PetCreateRequest request);

}
