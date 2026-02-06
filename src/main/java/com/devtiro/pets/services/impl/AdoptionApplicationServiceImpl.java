package com.devtiro.pets.services.impl;

import com.devtiro.pets.domain.dto.AdoptionApplicationCreateRequest;
import com.devtiro.pets.domain.dto.AdoptionApplicationDto;
import com.devtiro.pets.domain.dto.AdoptionApplicationUpdateRequest;
import com.devtiro.pets.domain.dto.AdoptionApplicationUpdateStatusRequest;
import com.devtiro.pets.domain.entity.*;
import com.devtiro.pets.exceptions.*;
import com.devtiro.pets.mappers.AdoptionApplicationMapper;
import com.devtiro.pets.repositories.ApplicationRepository;
import com.devtiro.pets.repositories.PetRepository;
import com.devtiro.pets.services.AdoptionApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdoptionApplicationServiceImpl implements AdoptionApplicationService {

    private final PetRepository petRepository;
    private final AdoptionApplicationMapper adoptionApplicationMapper;
    private final ApplicationRepository applicationRepository;

    @Override
    public AdoptionApplicationDto createApplication(AdoptionApplicationCreateRequest request, User applicant) {
        log.info("Creating adoption application for pet {} by user {}", request.getPetId(), applicant.getUsername());

        Pet pet = petRepository.findById(request.getPetId())
                .orElseThrow(() -> new PetNotFoundException("Pet not found with id: " + request.getPetId()));

        if (!pet.getStatus().equals(PetStatus.AVAILABLE)) {
            throw new InvalidApplicationStatusException("This pet is not available for adoption");
        }

        checkDuplicateApplication(pet.getId(), applicant.getId());

        AdoptionApplication application = adoptionApplicationMapper.toEntity(request);
        application.setPetId(pet.getId());
        application.setApplicantId(applicant.getId());
        application.setPetName(pet.getName());
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

        if (!application.getApplicantId().equals(applicant.getId())) {
            throw new UnauthorizedException("You can only update your own applications");
        }

        // Can only update draft applications
        if (!application.getStatus().equals(AdoptionApplicationStatus.DRAFT)) {
            throw new InvalidApplicationStatusException("Only draft applications can be updated");
        }

        String currentAdditionalComments = getAdditionalComments(application, request.getAdditionalComments());
        if (currentAdditionalComments != null) {
            application.setAdditionalComments(currentAdditionalComments);
        }

        adoptionApplicationMapper.updateEntity(application, request);

        AdoptionApplication updated = applicationRepository.save(application);
        log.info("Updated adoption application {}", updated.getId());

        return adoptionApplicationMapper.toAdoptionApplicationDto(updated);
    }

    @Override
    public AdoptionApplicationDto submitApplication(String applicationId, User applicant) {
        log.info("Submitting adoption application {} by user {}", applicationId, applicant.getUsername());

        AdoptionApplication existingApplication = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found: " + applicationId));

        if (!existingApplication.getApplicantId().equals(applicant.getId())) {
            throw new UnauthorizedException("You can only submit your own applications");
        }

        if (!existingApplication.getStatus().equals(AdoptionApplicationStatus.DRAFT)) {
            throw new InvalidApplicationStatusException("Only draft applications can be submitted");
        }

        checkDuplicateApplication(existingApplication.getPetId(), existingApplication.getApplicantId());

        existingApplication.setStatus(AdoptionApplicationStatus.SUBMITTED);
        existingApplication.setSubmittedAt(LocalDateTime.now());

        AdoptionApplication submitted = applicationRepository.save(existingApplication);
        log.info("Submitted adoption application {}", submitted.getId());

        // TODO send confirmation notification to applicant

        return adoptionApplicationMapper.toAdoptionApplicationDto(submitted);
    }

    @Override
    public AdoptionApplicationDto getApplicationById(String applicationId, User userPrincipal) {
        log.info("Getting adoption application {} for user {}", applicationId, userPrincipal.getUsername());

        AdoptionApplication existingApplication = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found: " + applicationId));

        // Users can only view their own applications, staff can view all
        if (!existingApplication.getApplicantId().equals(userPrincipal.getId()) && !userPrincipal.getRole().equals(Role.STAFF)) {
            throw new UnauthorizedException("You can only view your own applications");
        }

        return adoptionApplicationMapper.toAdoptionApplicationDto(existingApplication);
    }

    @Override
    public Page<AdoptionApplicationDto> getMyApplications(User applicant, Pageable pageable) {
        log.info("Getting applications for user {}", applicant.getUsername());
        return applicationRepository.findByApplicantId(applicant.getId(), pageable)
                .map(adoptionApplicationMapper::toAdoptionApplicationDto);
    }

    @Override
    public Page<AdoptionApplicationDto> getApplicationsForPet(String petId, Pageable pageable) {
        log.info("Getting applications for pet {}", petId);

        // Verify pet exists
        petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException("Pet not found: " + petId));

        Page<AdoptionApplication> applications = applicationRepository.findAllByPetId(petId, pageable);
        return applications.map(adoptionApplicationMapper::toAdoptionApplicationDto);
    }

    @Override
    public Page<AdoptionApplicationDto> getAllApplications(Pageable pageable) {
        log.info("Getting all applications");

        Page<AdoptionApplication> applications = applicationRepository.findAll(pageable);
        return  applications.map(adoptionApplicationMapper::toAdoptionApplicationDto);
    }

    @Override
    public Page<AdoptionApplicationDto> getApplicationsByStatus(AdoptionApplicationStatus status, Pageable pageable) {
        Page<AdoptionApplication> applications = applicationRepository.findAllByStatus(status, pageable);
        return applications.map(adoptionApplicationMapper::toAdoptionApplicationDto);
    }

    @Override
    public AdoptionApplicationDto updateApplicationStatus(String applicationId, AdoptionApplicationUpdateStatusRequest request, User staff) {
        log.info("Updating status of application {} to {} by staff {}", applicationId, request.getStatus(), staff.getUsername());

        AdoptionApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found: " + applicationId));

        application.setStatus(request.getStatus());

        String currentStaffNotes = getStaffNotes(application, request.getStaffNotes());
        if (currentStaffNotes != null) {
            application.setStaffNotes(currentStaffNotes);
        }

        AdoptionApplication updated = applicationRepository.save(application);
        log.info("Updated application {} status to {}", updated.getId(), updated.getStatus());

        return adoptionApplicationMapper.toAdoptionApplicationDto(updated);
    }

    @Override
    public AdoptionApplicationDto withdrawApplication(String applicationId, User applicant) {
        log.info("Withdrawing application {} by user {}", applicationId, applicant.getUsername());

        AdoptionApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found: " + applicationId));

        // Verify ownership
        if (!application.getApplicantId().equals(applicant.getId())) {
            throw new UnauthorizedException("You can only withdraw your own applications");
        }

        // Can only withdraw submitted applications
        if (application.getStatus().equals(AdoptionApplicationStatus.DRAFT)) {
            throw new InvalidApplicationStatusException("Draft applications should be deleted, not withdrawn");
        }

        if (application.getStatus().equals(AdoptionApplicationStatus.WITHDRAWN)) {
            throw new InvalidApplicationStatusException("Application has already been withdrawn");
        }

        // Update status
        application.setStatus(AdoptionApplicationStatus.WITHDRAWN);

        AdoptionApplication withdrawn = applicationRepository.save(application);
        log.info("Withdrew application {}", withdrawn.getId());

        return adoptionApplicationMapper.toAdoptionApplicationDto(withdrawn);
    }

    @Override
    public void deleteApplication(String applicationId, User applicant) {
        log.info("Deleting application {} by user {}", applicationId, applicant.getUsername());

        AdoptionApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found: " + applicationId));

        // Verify ownership
        if (!application.getApplicantId().equals(applicant.getId())) {
            throw new UnauthorizedException("You can only withdraw your own applications");
        }
        // Can only delete draft applications
        if (!application.getStatus().equals(AdoptionApplicationStatus.DRAFT)) {
            throw new InvalidApplicationStatusException("Only draft applications can be deleted");
        }

        applicationRepository.delete(application);
        log.info("Deleted application {}", applicationId);
    }

    // TODO create validatorservice for this and pet
    private void checkDuplicateApplication(String petId, String applicantId) {
        // Check if user already has a submitted (non-draft) application for this pet
        Optional<AdoptionApplication> optionalAdoptionApplication = applicationRepository
                .findByPetIdAndApplicantId(
                        petId,
                        applicantId
                );
        if (optionalAdoptionApplication.isPresent()
                && !optionalAdoptionApplication.get().getStatus().equals(AdoptionApplicationStatus.DRAFT)) {
            throw new DuplicateApplicationException("You have already submitted an application for this pet");
        }
    }

    /**
     * Get the staff notes that the application to be updated with
     */
    private String getStaffNotes(AdoptionApplication application, String staffNotes) {
        // request staff notes is empty, if the application staff notes is null then return null, otherwise
        // just return the original staff notes
        if (staffNotes == null) {
            return application.getStaffNotes() == null ? null : application.getStaffNotes();
        }
        // application staff notes is empty, but request staff notes is not empty just return that
        if (application.getStaffNotes() == null) {
            return staffNotes;
        }
        // both are non-empty, concatenate them
        return application.getStaffNotes() + "\n" + staffNotes;
    }


    /**
     * Get the additional comments that the application to be updated with
     */
    private String getAdditionalComments(AdoptionApplication application, String additionalComments) {
        if (additionalComments == null) {
            return application.getAdditionalComments() == null ? null : application.getAdditionalComments();
        }
        if (application.getAdditionalComments() == null) {
            return additionalComments;
        }
        return application.getAdditionalComments() + "\n" + additionalComments;
    }

}
