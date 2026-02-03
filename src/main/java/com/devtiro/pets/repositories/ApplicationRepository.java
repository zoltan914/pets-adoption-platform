package com.devtiro.pets.repositories;

import com.devtiro.pets.domain.entity.AdoptionApplication;
import com.devtiro.pets.domain.entity.AdoptionApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends ElasticsearchRepository<AdoptionApplication, String> {

    /**
     * Check if a user has already submitted (non-draft) application for a pet
     */
    Optional<AdoptionApplication> findByPetIdAndApplicantId(String petId, String applicantId);

    /**
     * Find all applications by applicant ID
     */
    Page<AdoptionApplication> findByApplicantId(String applicantId, Pageable pageable);

    /**
     * Find all applications for a specific pet
     */
    Page<AdoptionApplication> findAllByPetId(String petId, Pageable pageable);
    /**
     * Find all applications by status
     */
    Page<AdoptionApplication> findAllByStatus(AdoptionApplicationStatus status, Pageable pageable);

}
