package com.wd_aljak.employee_management.repo;

import com.wd_aljak.employee_management.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LeaveRequestRepo extends JpaRepository<LeaveRequest, UUID> {
    List<LeaveRequest> findAllByEmployee_Id(UUID employeeId);
}
