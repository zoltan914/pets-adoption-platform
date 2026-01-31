package com.devtiro.pets.repositories;

import com.devtiro.pets.domain.entity.Pet;
import com.devtiro.pets.domain.entity.PetStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends ElasticsearchRepository<Pet, String> {
    @Override
    List<Pet> findAll();

    Page<Pet> findAllByStatus(PetStatus status, Pageable pageable);

}
