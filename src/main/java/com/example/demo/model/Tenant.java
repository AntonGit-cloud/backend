package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("tenant")
public class Tenant {
    @Id
    private String id;

    @DBRef
    private User user;

    @CreatedDate
    private LocalDateTime createdAt;
}
