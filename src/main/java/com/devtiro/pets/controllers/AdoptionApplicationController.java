package com.devtiro.pets.controllers;

import com.devtiro.pets.domain.dto.AdoptionApplicationCreateRequest;
import com.devtiro.pets.domain.dto.AdoptionApplicationDto;
import com.devtiro.pets.domain.entity.User;
import com.devtiro.pets.services.AdoptionApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class AdoptionApplicationController {

    private final AdoptionApplicationService adoptionApplicationService;

    @PostMapping
    public ResponseEntity<AdoptionApplicationDto> createAdoptionApplication(
            @Valid @RequestBody AdoptionApplicationCreateRequest request,
            @AuthenticationPrincipal User user
    ) {
        AdoptionApplicationDto application = adoptionApplicationService.createApplication(request, user);
        return new ResponseEntity<>(application, HttpStatus.CREATED);
    }
}
