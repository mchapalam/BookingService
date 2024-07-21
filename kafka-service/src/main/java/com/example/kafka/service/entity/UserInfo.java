package com.example.kafka.service.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Document(collection = "userInfo")
public class UserInfo {
    @Id
    private UUID id;

    private LocalDate dateCreate;
}
