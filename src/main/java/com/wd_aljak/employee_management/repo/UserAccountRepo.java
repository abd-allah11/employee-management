package com.wd_aljak.employee_management.repo;

import com.wd_aljak.employee_management.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAccountRepo extends JpaRepository<UserAccount, UUID> {

    Optional<UserAccount> findOneByUsername(String username);

}
