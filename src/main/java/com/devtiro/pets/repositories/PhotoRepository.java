package com.devtiro.pets.repositories;

import com.devtiro.pets.domain.entity.Photo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends ElasticsearchRepository<Photo, String> {

    List<Photo> findAllByPetId(String petId);

}
