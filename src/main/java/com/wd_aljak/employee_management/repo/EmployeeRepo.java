package com.wd_aljak.employee_management.repo;

import com.wd_aljak.employee_management.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepo extends JpaRepository<Employee, UUID> {
}
