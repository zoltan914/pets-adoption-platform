package com.devtiro.pets.repositories;

import com.devtiro.pets.domain.entity.Pet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends ElasticsearchRepository<Pet, String> {
}
