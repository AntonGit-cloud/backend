package com.backend.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("institutions")
public class EducationalInstitution {
    @Id
    private String id;

    private String name;

}
