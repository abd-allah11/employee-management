package com.wd_aljak.employee_management.services;

import com.wd_aljak.employee_management.abstracts.LeaveRequestService;
import com.wd_aljak.employee_management.dtos.LeaveRequestCreateDTO;
import com.wd_aljak.employee_management.model.Employee;
import com.wd_aljak.employee_management.model.LeaveRequest;
import com.wd_aljak.employee_management.repo.EmployeeRepo;
import com.wd_aljak.employee_management.repo.LeaveRequestRepo;
import com.wd_aljak.employee_management.shared.CustomResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {
    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    LeaveRequestRepo leaveRequestRepo;

    @Override

    public LeaveRequest createOne(
            LeaveRequestCreateDTO leaveRequestCreate,
            UUID employeeId
    ) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() ->
                        CustomResponseException.ResponseNotFound(
                                "Employee with id -> " + employeeId + " Not found"
                        )
                );

        if (leaveRequestCreate.endDate().isBefore(leaveRequestCreate.startDate())) {
            throw CustomResponseException.BadRequest("End date must be on or after start date");
        }

        LeaveRequest leaveRequest = new LeaveRequest();

        leaveRequest.setState("PENDING");
        leaveRequest.setReason(leaveRequestCreate.reason());
        leaveRequest.setStartDate(leaveRequestCreate.startDate());
        leaveRequest.setEndDate(leaveRequestCreate.endDate());
        leaveRequest.setEmployee(employee);

        leaveRequestRepo.save(leaveRequest);

        return leaveRequest;
    }


    @Override
    public List<LeaveRequest> findAllByEmployee_Id(UUID employeeId) {
        return leaveRequestRepo.findAllByEmployee_Id(employeeId);
    }
}