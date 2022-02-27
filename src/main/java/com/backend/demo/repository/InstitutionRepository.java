package com.backend.demo.repository;

import com.backend.demo.model.EducationalInstitution;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitutionRepository extends MongoRepository<EducationalInstitution, String> {

}
