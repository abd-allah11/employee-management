package com.wd_aljak.employee_management.repo;

import com.wd_aljak.employee_management.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PasswordRestRepo extends JpaRepository<PasswordResetToken, UUID> {
    Optional<PasswordResetToken> findOneByToken(String token);
}