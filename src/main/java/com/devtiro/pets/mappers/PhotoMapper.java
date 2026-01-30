package com.devtiro.pets.mappers;

import com.devtiro.pets.domain.dto.PhotoDto;
import com.devtiro.pets.domain.entity.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PhotoMapper {

    List<PhotoDto> toPhotoDto(List<Photo> photos);

    List<Photo> toEntity(List<PhotoDto> photos);
}
