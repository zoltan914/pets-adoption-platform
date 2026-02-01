package com.devtiro.pets.services;

import com.devtiro.pets.domain.dto.PetDto;
import com.devtiro.pets.domain.dto.PetSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PetSearchService {

    Page<PetDto> searchPets(PetSearchRequest request, Pageable pageable);

}
