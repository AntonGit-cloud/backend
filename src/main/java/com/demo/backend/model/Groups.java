package com.demo.backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("groups")
public class Groups {
    @Id
    private String id;

    private String name;

    @DBRef
    private List<Student> studentList;
}
