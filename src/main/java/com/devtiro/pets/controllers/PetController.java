package com.devtiro.pets.controllers;

import com.devtiro.pets.domain.dto.*;
import com.devtiro.pets.domain.entity.User;
import com.devtiro.pets.services.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @GetMapping
    @PreAuthorize("hasRole('STAFF')")
    public Page<PetDto> getAllPets(
            @PageableDefault(
                    size = 20,
                    page = 0,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return petService.getAllPets(pageable);
    }

    @GetMapping("/{petId}")
    @PreAuthorize("hasAnyRole('STAFF','USER')")
    public PetDto getAvailablePetById(
            @PathVariable("petId") String petId
    ) {
        return petService.getAvailablePetById(petId);
    }

    @GetMapping("/available")
    @PreAuthorize("hasAnyRole('STAFF','USER')")
    public Page<PetDto> getAllAvailablePets(
            @PageableDefault(
                    size = 20,
                    page = 0,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return petService.getAllAvailablePets(pageable);
    }

    @PostMapping
    @PreAuthorize("hasRole('STAFF')")
    public PetDto createPet(
            @Valid @RequestBody PetCreateRequest request,
            @AuthenticationPrincipal User staff
    ) {
        return petService.createPet(request, staff);
    }

    @PutMapping("/{petId}")
    @PreAuthorize("hasRole('STAFF')")
    public PetDto updatePet(
            @PathVariable String petId,
            @Valid @RequestBody PetUpdateRequest request
    ) {
        return petService.updatePet(petId, request);
    }

    @PatchMapping("/{petId}/status")
    @PreAuthorize("hasRole('STAFF')")
    public PetDto updatePetStatus(
            @PathVariable String petId,
            @Valid @RequestBody PetStatusUpdateRequest request
    ) {
        return petService.updatePetStatus(petId, request);
    }

    @DeleteMapping("/{petId}")
    @PreAuthorize("hasRole('STAFF')")
    public void deletePet(
            @PathVariable String petId
    ) {
        petService.deletePet(petId);
    }

    @PostMapping("/search")
    @PreAuthorize("hasAnyRole('STAFF','USER')")
    public Page<PetDto> searchPets(
            @RequestBody PetSearchRequest request,
            @PageableDefault(
                    size = 20,
                    page = 0
            )
            @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return petService.searchPets(request, pageable);
    }
}
