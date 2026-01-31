package com.devtiro.pets.controllers;

import com.devtiro.pets.domain.dto.PhotoDto;
import com.devtiro.pets.domain.dto.PhotoUploadRequest;
import com.devtiro.pets.services.PhotoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/photos")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @GetMapping("/{petId}")
    public List<PhotoDto> getAllPhotosByPetId(
            @PathVariable String petId
    ) {
        return photoService.getAllPhotosByPetId(petId);
    }

    @PostMapping("/{petId}")
    public List<PhotoDto> uploadPhotos(
            @PathVariable String petId,
            @Valid @RequestBody List<PhotoUploadRequest> requests
    ) {
        return photoService.uploadPhotos(petId, requests);
    }

}
