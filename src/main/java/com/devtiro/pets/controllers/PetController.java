package com.devtiro.pets.controllers;

import com.devtiro.pets.domain.dto.PetCreateRequest;
import com.devtiro.pets.domain.dto.PetDto;
import com.devtiro.pets.domain.dto.PhotoDto;
import com.devtiro.pets.domain.dto.PhotoUploadRequest;
import com.devtiro.pets.domain.entity.User;
import com.devtiro.pets.services.PetService;
import com.devtiro.pets.services.PhotoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @PostMapping
    public PetDto createPet(
            @Valid @RequestBody PetCreateRequest request,
            @AuthenticationPrincipal User staff
    ) {
        return petService.createPet(request, staff);
    }

}
