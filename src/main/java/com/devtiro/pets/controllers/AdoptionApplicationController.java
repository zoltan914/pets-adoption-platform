package com.devtiro.pets.controllers;

import com.devtiro.pets.domain.dto.AdoptionApplicationCreateRequest;
import com.devtiro.pets.domain.dto.AdoptionApplicationDto;
import com.devtiro.pets.domain.dto.AdoptionApplicationUpdateRequest;
import com.devtiro.pets.domain.entity.User;
import com.devtiro.pets.services.AdoptionApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class AdoptionApplicationController {

    private final AdoptionApplicationService adoptionApplicationService;

    /**
     * Create a new adoption application
     * Can be saved as draft (isDraft=true) or submitted immediately (isDraft=false)
     * Users cannot submit multiple applications for the same pet
     */
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AdoptionApplicationDto> createAdoptionApplication(
            @Valid @RequestBody AdoptionApplicationCreateRequest request,
            @AuthenticationPrincipal User applicant
    ) {
        AdoptionApplicationDto application = adoptionApplicationService.createApplication(request, applicant);
        return new ResponseEntity<>(application, HttpStatus.CREATED);
    }

    /**
     * Update a draft application
     * Only the applicant can update their own draft applications
     */
    @PutMapping("/{applicationId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AdoptionApplicationDto> updateApplication(
            @PathVariable String applicationId,
            @Valid @RequestBody AdoptionApplicationUpdateRequest request,
            @AuthenticationPrincipal User applicant
    ) {

        AdoptionApplicationDto response = adoptionApplicationService.updateApplication(
                applicationId, request, applicant);
        return ResponseEntity.ok(response);
    }

    /**
     * Submit a draft application
     * Changes status from DRAFT to SUBMITTED
     */
    @PostMapping("/{applicationId}/submit")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AdoptionApplicationDto> submitApplication(
            @PathVariable String applicationId,
            @AuthenticationPrincipal User applicant
    ) {
        AdoptionApplicationDto response = adoptionApplicationService.submitApplication(
                applicationId, applicant);

        return ResponseEntity.ok(response);
    }

    /**
     * Get a specific application by ID
     * Users can only view their own applications
     * Staff can view any application
     */
    @GetMapping("/{applicationId}")
    @PreAuthorize("hasAnyRole('USER', 'STAFF')")
    public ResponseEntity<AdoptionApplicationDto> getApplication(
            @PathVariable String applicationId,
            @AuthenticationPrincipal User userPrincipal
    ) {
        AdoptionApplicationDto response = adoptionApplicationService.getApplicationById(
                applicationId, userPrincipal);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-applications")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<AdoptionApplicationDto>> getMyApplications(
            @PageableDefault(
                    size = 20,
                    page = 0,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal User applicant
    ) {
        Page<AdoptionApplicationDto> response = adoptionApplicationService.getMyApplications(
                applicant, pageable);
        return ResponseEntity.ok(response);
    }


}
