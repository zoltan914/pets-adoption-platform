package com.devtiro.pets.services;

import com.devtiro.pets.domain.dto.PetCreateRequest;
import com.devtiro.pets.domain.dto.PetDto;
import com.devtiro.pets.domain.dto.PhotoDto;
import com.devtiro.pets.domain.dto.PhotoUploadRequest;
import com.devtiro.pets.domain.entity.User;

import java.util.List;

public interface PetService {

    PetDto createPet(PetCreateRequest request, User staff);

}
