package com.wd_aljak.employee_management.controller;

import com.wd_aljak.employee_management.model.Employee;
import com.wd_aljak.employee_management.shared.CustomResponseException;
import com.wd_aljak.employee_management.shared.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    ArrayList<Employee> employees = new ArrayList<>();

    @GetMapping
    public ResponseEntity<GlobalResponse<ArrayList<Employee>>> getEmployees() {
        return new ResponseEntity<>(new GlobalResponse<>(employees), HttpStatus.OK);

    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<GlobalResponse<Employee>> getEmployee(@PathVariable("employeeId") UUID employeeId) {
        Employee employee = employees.stream()
                .filter(emp -> emp.getId().equals(employeeId))
                .findFirst()
                .orElseThrow(() -> CustomResponseException.ResponseNotFound("Employee with id -> " + employeeId + "Not found"));
        return new ResponseEntity<>(new GlobalResponse<>(employee), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<Employee>> addEmployee(@RequestBody @Valid Employee employee) {
        employee.setId(UUID.randomUUID());
        employee.setDepartmentId(UUID.randomUUID());
        employees.add(employee);
        return new ResponseEntity<>(new GlobalResponse<>(employee), HttpStatus.CREATED);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID employeeId) {
        Optional<Employee> employee = employees.stream()
                .filter(emp -> emp.getId().equals(employeeId)).findFirst();
        employee.ifPresent(value -> employees.remove(value));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<GlobalResponse<Employee>> updateEmployee(@RequestBody Employee employee, @PathVariable UUID employeeId) {
        Employee existEmployee = employees.stream()
                .filter(emp -> emp.getId()
                        .equals(employeeId))
                .findFirst()
                .orElseThrow(() -> CustomResponseException.ResponseNotFound("Employee with id -> " + employeeId + "Not found"));
        ;

        existEmployee.setFirstName(employee.getFirstName());
        existEmployee.setLastName(employee.getLastName());
        existEmployee.setEmail(employee.getEmail());
        existEmployee.setHireDate(employee.getHireDate());
        existEmployee.setPosition(employee.getPosition());
        existEmployee.setPhoneNumber(employee.getPhoneNumber());
        existEmployee.setDepartmentId(employee.getDepartmentId());
        return new ResponseEntity<>(new GlobalResponse<>(existEmployee), HttpStatus.OK);
    }
}
