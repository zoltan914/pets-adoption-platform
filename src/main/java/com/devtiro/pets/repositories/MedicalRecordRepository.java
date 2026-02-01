package com.devtiro.pets.repositories;

import com.devtiro.pets.domain.entity.MedicalRecord;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends ElasticsearchRepository<MedicalRecord, String> {
    List<MedicalRecord> findAllByPetId(String petId);
}
