package com.devtiro.pets.services.impl;

import com.devtiro.pets.domain.dto.AdoptionApplicationCreateRequest;
import com.devtiro.pets.domain.dto.AdoptionApplicationDto;
import com.devtiro.pets.domain.dto.AdoptionApplicationUpdateRequest;
import com.devtiro.pets.domain.entity.*;
import com.devtiro.pets.exceptions.*;
import com.devtiro.pets.mappers.AdoptionApplicationMapper;
import com.devtiro.pets.repositories.ApplicationRepository;
import com.devtiro.pets.repositories.PetRepository;
import com.devtiro.pets.repositories.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    public AdoptionApplicationDto createApplication(AdoptionApplicationCreateRequest request, User applicant) {
        log.info("Creating adoption application for pet {} by user {}", request.getPetId(), applicant.getUsername());

        Pet pet = petRepository.findById(request.getPetId())
                .orElseThrow(() -> new PetNotFoundException("Pet not found with id: " + request.getPetId()));

        if (!pet.getStatus().equals(PetStatus.AVAILABLE)) {
            throw new InvalidApplicationStatusException("This pet is not available for adoption");
        }

        // Check if user already has a submitted (non-draft) application for this pet
        boolean hasExistingApplication = applicationRepository
                .findByPetIdAndApplicantId(
                        request.getPetId(),
                        applicant.getId()
                ).isPresent();

        if (hasExistingApplication) {
            throw new DuplicateApplicationException("You have already submitted an application for this pet");
        }

        AdoptionApplication application = adoptionApplicationMapper.toEntity(request);
        application.setPetId(pet.getId());
        application.setPet(pet);
        application.setApplicantId(applicant.getId());
        application.setApplicant(applicant);
        application.setFirstName(applicant.getFirstName());
        application.setLastName(applicant.getLastName());
        application.setEmail(applicant.getEmail());
        application.setPhoneNumber(applicant.getPhoneNumber());

        // Set submitted time if not a draft
        if (Boolean.TRUE.equals(request.getIsDraft())) {
            application.setStatus(AdoptionApplicationStatus.DRAFT);
        } else {
            application.setStatus(AdoptionApplicationStatus.SUBMITTED);
            application.setSubmittedAt(LocalDateTime.now());
        }

        AdoptionApplication saved = applicationRepository.save(application);
        log.info("Created adoption application {} with status {}", saved.getId(), saved.getStatus());

        return adoptionApplicationMapper.toAdoptionApplicationDto(saved);
    }

    @Override
    public AdoptionApplicationDto updateApplication(String applicationId, AdoptionApplicationUpdateRequest request, User applicant) {
        log.info("Updating adoption application {} by user {}", applicationId, applicant.getUsername());

        AdoptionApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found: " + applicationId));

        // Verify ownership
        User verifiedUser = userRepository.findByUsername(applicant.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + applicant.getUsername()));

        if (!application.getApplicantId().equals(verifiedUser.getId())) {
            throw new UnauthorizedException("You can only update your own applications");
        }

        // Can only update draft applications
        if (!application.getStatus().equals(AdoptionApplicationStatus.DRAFT)) {
            throw new InvalidApplicationStatusException("Only draft applications can be updated");
        }

        if (!application.getAdditionalComments().isEmpty()) {
            String additionalCommentsAppended = application.getAdditionalComments().concat("\n").concat(request.getAdditionalComments());
            request.setAdditionalComments(additionalCommentsAppended);
        }

        adoptionApplicationMapper.updateEntity(application, request);

        AdoptionApplication updated = applicationRepository.save(application);
        log.info("Updated adoption application {}", updated.getId());

        return adoptionApplicationMapper.toAdoptionApplicationDto(updated);
    }
}
