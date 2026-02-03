package com.devtiro.pets.repositories;

import com.devtiro.pets.domain.entity.AdoptionApplication;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends ElasticsearchRepository<AdoptionApplication, String> {

    /**
     * Check if a user has already submitted (non-draft) application for a pet
     */
    Optional<AdoptionApplication> findByPetIdAndApplicantId(String petId, String applicantId);

}
