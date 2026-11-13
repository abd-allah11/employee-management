package com.wd_aljak.employee_management.repo;

import com.wd_aljak.employee_management.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, UUID> {
    Optional<Employee> findOneByAccountCreationToken(String findOneByAccountCreationToken);
}