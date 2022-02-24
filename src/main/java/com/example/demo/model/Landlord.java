package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("landlord")
public class Landlord {
    @Id
    private String id;

    @DBRef
    private User user;

    private String manualLeasingTool;

    @CreatedDate
    private LocalDateTime createdAt;

}
