package com.devtiro.pets.services.impl;

import com.devtiro.pets.domain.dto.PetDto;
import com.devtiro.pets.domain.dto.PhotoDto;
import com.devtiro.pets.domain.dto.PhotoUploadRequest;
import com.devtiro.pets.domain.entity.Pet;
import com.devtiro.pets.domain.entity.Photo;
import com.devtiro.pets.exceptions.PetNotFoundException;
import com.devtiro.pets.mappers.PhotoMapper;
import com.devtiro.pets.repositories.PetRepository;
import com.devtiro.pets.repositories.PhotoRepository;
import com.devtiro.pets.services.PhotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final PetRepository petRepository;
    private final PhotoRepository photoRepository;
    private final PhotoMapper photoMapper;

    @Override
    public List<PhotoDto> getAllPhotosByPetId(String petId) {
        return photoMapper.toPhotoDto(photoRepository.findAllByPetId(petId));
    }

    @Override
    public List<PhotoDto> uploadPhotos(String petId, List<PhotoUploadRequest> requests) {
        String existingPetId = petRepository.findById(petId)
                .map(Pet::getId)
                .orElseThrow(() -> new PetNotFoundException("Pet not found with id: " + petId));

        List<Photo> uploadedPhotos = requests.stream()
                .map(photoRequest -> {
                    Photo newPhoto = Photo.builder()
                            .petId(existingPetId)
                            .url(photoRequest.getUrl())
                            .build();
                    return photoRepository.save(newPhoto);
                }).toList();

        return photoMapper.toPhotoDto(uploadedPhotos);
    }
}
