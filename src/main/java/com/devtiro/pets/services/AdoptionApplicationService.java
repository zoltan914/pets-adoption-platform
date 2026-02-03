package com.devtiro.pets.services;

import com.devtiro.pets.domain.dto.AdoptionApplicationCreateRequest;
import com.devtiro.pets.domain.dto.AdoptionApplicationDto;
import com.devtiro.pets.domain.dto.AdoptionApplicationUpdateRequest;
import com.devtiro.pets.domain.entity.AdoptionApplication;
import com.devtiro.pets.domain.entity.User;

public interface AdoptionApplicationService {

    /**
     * Create a new adoption application
     * Can be saved as draft or submitted
     */
    AdoptionApplicationDto createApplication(AdoptionApplicationCreateRequest request, User applicant);

    AdoptionApplicationDto updateApplication(String applicationId, AdoptionApplicationUpdateRequest request, User applicant);

    AdoptionApplicationDto submitApplication(String applicationId, User applicant);

    AdoptionApplicationDto getApplicationById(String applicationId, User userPrincipal);

}
