package com.devtiro.pets.controllers;

import com.devtiro.pets.domain.dto.PetCreateRequest;
import com.devtiro.pets.domain.dto.PetDto;
import com.devtiro.pets.domain.dto.PetStatusUpdateRequest;
import com.devtiro.pets.domain.dto.PetUpdateRequest;
import com.devtiro.pets.domain.entity.User;
import com.devtiro.pets.services.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public List<PetDto> getAllPets() {
        return petService.getAllPets();
    }

    // USER role
    @GetMapping("/available")
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
    public PetDto createPet(
            @Valid @RequestBody PetCreateRequest request,
            @AuthenticationPrincipal User staff
    ) {
        return petService.createPet(request, staff);
    }

    @PutMapping("/{petId}")
    public PetDto updatePet(
            @PathVariable String petId,
            @Valid @RequestBody PetUpdateRequest request
    ) {
        return petService.updatePet(petId, request);
    }

    @PatchMapping("/{petId}/status")
    public PetDto updatePetStatus(
            @PathVariable String petId,
            @Valid @RequestBody PetStatusUpdateRequest request
    ) {
        return petService.updatePetStatus(petId, request);
    }

    @DeleteMapping("/{petId}")
    public void deletePet(
            @PathVariable String petId
    ) {
        petService.deletePet(petId);
    }
}
