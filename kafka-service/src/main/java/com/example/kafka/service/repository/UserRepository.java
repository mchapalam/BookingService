package com.example.kafka.service.repository;

import com.example.kafka.service.entity.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<UserInfo, UUID> {
}
