package com.devtiro.pets.repositories;

import com.devtiro.pets.domain.entity.MedicalRecord;
import com.devtiro.pets.domain.entity.Pet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordRepository extends ElasticsearchRepository<MedicalRecord, String> {
}
