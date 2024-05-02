package com.security.secretstore.core.repository;

import com.security.secretstore.core.entity.Secret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SecretRepository extends JpaRepository<Secret, UUID> {
}
