package com.wd_aljak.employee_management.controller;

import com.wd_aljak.employee_management.dtos.EmployeeCreateDTO;
import com.wd_aljak.employee_management.dtos.EmployeeUpdateDTO;
import com.wd_aljak.employee_management.dtos.LeaveRequestCreateDTO;
import com.wd_aljak.employee_management.dtos.PaginatedResponse;
import com.wd_aljak.employee_management.model.Employee;
import com.wd_aljak.employee_management.model.LeaveRequest;
import com.wd_aljak.employee_management.services.EmployeeServiceImpl;
import com.wd_aljak.employee_management.services.LeaveRequestServiceImpl;
import com.wd_aljak.employee_management.shared.GlobalResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeServiceImpl employeeService;

    @Autowired
    LeaveRequestServiceImpl leaveRequestService;


    @GetMapping
    public ResponseEntity<GlobalResponse<PaginatedResponse<Employee>>> findall(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "3") int size,
            HttpServletRequest request
    ) {
        // Validate pagination parameters
        if (page < 1 || size < 1) {
            throw new IllegalArgumentException("Page must be >= 1 and size must be >= 1");
        }

        int zeroBasedPage = page - 1;

        Page<Employee> employees = employeeService.findAll(zeroBasedPage, size);

        String baseUrl = request.getRequestURL().toString();

        String nextUrl = employees.hasNext()
                ? String.format("%s?page=%d&size=%d", baseUrl, page + 1, size)
                : null;

        String prevUrl = employees.hasPrevious()
                ? String.format("%s?page=%d&size=%d", baseUrl, page - 1, size)
                : null;

        var paginatedResponse = new PaginatedResponse<Employee>(
                employees.getContent(),
                page,
                employees.getTotalPages(),
                employees.getTotalElements(),
                employees.hasNext(),
                employees.hasPrevious(),
                nextUrl,
                prevUrl
        );

        return new ResponseEntity<>(
                new GlobalResponse<>(paginatedResponse),
                HttpStatus.OK
        );
    }


    @GetMapping("/{employeeId}")
    public ResponseEntity<GlobalResponse<Employee>> findOne(@PathVariable("employeeId") UUID employeeId) {
        Employee employee = employeeService.findOne(employeeId);
        return new ResponseEntity<>(new GlobalResponse<>(employee), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<Employee>> createOne(@RequestBody @Valid EmployeeCreateDTO employee) {
        Employee newEmployee = employeeService.createOne(employee);
        return new ResponseEntity<>(new GlobalResponse<>(newEmployee), HttpStatus.CREATED);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteOne(@PathVariable UUID employeeId) {
        employeeService.deleteOne(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<GlobalResponse<Employee>> updateOne(@RequestBody EmployeeUpdateDTO employee, @PathVariable UUID employeeId) {
        Employee updatedEmployee = employeeService.updateOne(employeeId, employee);
        return new ResponseEntity<>(new GlobalResponse<>(updatedEmployee), HttpStatus.OK);
    }

    @PostMapping("/{employeeId}/leave-request")
    public ResponseEntity<GlobalResponse<LeaveRequest>> leaveRequest(
            @RequestBody @Valid LeaveRequestCreateDTO leaveRequest,
            @PathVariable UUID employeeId) {
        LeaveRequest newLeaveRequest = leaveRequestService.createOne(leaveRequest, employeeId);
        return new ResponseEntity<>(new GlobalResponse<>(newLeaveRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{employeeId}/leave-requests")
    public ResponseEntity<GlobalResponse<List<LeaveRequest>>> findAllByEmployeeId(
            @PathVariable UUID employeeId
    ) {
        List<LeaveRequest> leaveRequests = leaveRequestService.findAllByEmployee_Id(employeeId);
        return new ResponseEntity<>(new GlobalResponse<>(leaveRequests), HttpStatus.OK);

    }
}
