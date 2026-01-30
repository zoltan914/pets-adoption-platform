package com.devtiro.pets.services;

import com.devtiro.pets.domain.dto.PetCreateRequest;
import com.devtiro.pets.domain.dto.PetResponse;
import com.devtiro.pets.domain.entity.User;

public interface PetService {

    PetResponse createPet(PetCreateRequest request, User staff);

}
