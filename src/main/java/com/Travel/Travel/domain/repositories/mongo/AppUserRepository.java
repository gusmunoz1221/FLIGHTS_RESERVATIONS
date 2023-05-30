package com.Travel.Travel.domain.repositories.mongo;

import com.Travel.Travel.domain.entities.documents.AppUserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface AppUserRepository extends MongoRepository<AppUserDocument,UUID> {
    Optional<AppUserDocument> findByUsername(String username);
}