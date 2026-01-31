package com.devtiro.pets.services;

import com.devtiro.pets.domain.dto.*;
import com.devtiro.pets.domain.entity.User;

import java.util.List;

public interface PetService {

    PetDto createPet(PetCreateRequest request, User staff);

    PetDto updatePet(String petId, PetUpdateRequest request);

}
