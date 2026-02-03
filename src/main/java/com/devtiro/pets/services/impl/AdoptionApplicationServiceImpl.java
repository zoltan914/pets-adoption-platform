package com.devtiro.pets.services.impl;

import com.devtiro.pets.domain.dto.AdoptionApplicationCreateRequest;
import com.devtiro.pets.domain.dto.AdoptionApplicationDto;
import com.devtiro.pets.domain.entity.*;
import com.devtiro.pets.exceptions.DuplicateApplicationException;
import com.devtiro.pets.exceptions.InvalidApplicationStatusException;
import com.devtiro.pets.exceptions.PetNotFoundException;
import com.devtiro.pets.mappers.AdoptionApplicationMapper;
import com.devtiro.pets.repositories.ApplicationRepository;
import com.devtiro.pets.repositories.PetRepository;
import com.devtiro.pets.services.AdoptionApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdoptionApplicationServiceImpl implements AdoptionApplicationService {

    private final PetRepository petRepository;
    private final AdoptionApplicationMapper adoptionApplicationMapper;
    private final ApplicationRepository applicationRepository;

    @Override
    public AdoptionApplicationDto createApplication(AdoptionApplicationCreateRequest request, User user) {
        Pet pet = petRepository.findById(request.getPetId())
                .orElseThrow(() -> new PetNotFoundException("Pet not found with id: " + request.getPetId()));

        if (!pet.getStatus().equals(PetStatus.AVAILABLE)) {
            throw new InvalidApplicationStatusException("This pet is not available for adoption");
        }

        // Check if user already has a submitted (non-draft) application for this pet
        boolean hasExistingApplication = applicationRepository
                .findByPetIdAndApplicantId(
                        request.getPetId(),
                        user.getId()
                ).isPresent();

        if (hasExistingApplication) {
            throw new DuplicateApplicationException("You have already submitted an application for this pet");
        }

        AdoptionApplication application = adoptionApplicationMapper.toAdoptionApplication(request, pet, user);

        // Set submitted time if not a draft
        if (Boolean.TRUE.equals(request.getIsDraft())) {
            application.setStatus(AdoptionApplicationStatus.DRAFT);
        } else {
            application.setStatus(AdoptionApplicationStatus.SUBMITTED);
            application.setSubmittedAt(LocalDateTime.now());
        }

        AdoptionApplication saved = applicationRepository.save(application);
        log.info("Created adoption application {} with status {}", saved.getId(), saved.getStatus());

        return adoptionApplicationMapper.toAdoptionApplicationDto(saved, pet, user);
    }
}
