package com.demo.backend.repository;

import com.demo.backend.model.EducationalInstitution;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitutionRepository extends MongoRepository<EducationalInstitution, String> {

}
