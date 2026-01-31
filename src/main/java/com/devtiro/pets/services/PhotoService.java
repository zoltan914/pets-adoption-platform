package com.devtiro.pets.services;


import com.devtiro.pets.domain.dto.PhotoDto;
import com.devtiro.pets.domain.dto.PhotoUploadRequest;

import java.util.List;

public interface PhotoService {

    List<PhotoDto> getAllPhotosByPetId(String petId);

    List<PhotoDto> uploadPhotos(String petId, List<PhotoUploadRequest> requests);

}
