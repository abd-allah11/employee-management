package com.wd_aljak.employee_management.services;

import com.wd_aljak.employee_management.abstracts.EmployeeService;
import com.wd_aljak.employee_management.dtos.EmployeeCreateDTO;
import com.wd_aljak.employee_management.dtos.EmployeeUpdateDTO;
import com.wd_aljak.employee_management.model.Employee;
import com.wd_aljak.employee_management.shared.CustomResponseException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    ArrayList<Employee> employees = new ArrayList<>();

    @Override
    public ArrayList<Employee> findAll() {
        return employees;
    }

    @Override
    public Employee findOne(UUID employeeId) {
        Employee employee = employees.stream()
                .filter(emp -> emp.getId().equals(employeeId))
                .findFirst()
                .orElseThrow(() -> CustomResponseException.ResponseNotFound("Employee with id -> " + employeeId + "Not found"));
        return employee;
    }

    @Override
    public Employee createOne(EmployeeCreateDTO employeeCreate) {
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setDepartmentId(UUID.randomUUID());
        employee.setFirstName(employeeCreate.firstName());
        employee.setLastName(employeeCreate.lastName());
        employee.setHireDate(employeeCreate.hireDate());
        employee.setPhoneNumber(employeeCreate.phoneNumber());
        employee.setEmail(employeeCreate.email());
        employees.add(employee);
        return employee;
    }

    @Override
    public void deleteOne(UUID employeeId) {
        Optional<Employee> employee = employees.stream()
                .filter(emp -> emp.getId().equals(employeeId)).findFirst();
        employee.ifPresent(value -> employees.remove(value));
    }

    @Override
    public Employee updateOne(UUID employeeId, EmployeeUpdateDTO employee) {
        Employee existEmployee = employees.stream()
                .filter(emp -> emp.getId()
                        .equals(employeeId))
                .findFirst()
                .orElseThrow(() -> CustomResponseException.ResponseNotFound("Employee with id -> " + employeeId + "Not found"));
        ;

        existEmployee.setFirstName(employee.firstName());
        existEmployee.setLastName(employee.lastName());
        existEmployee.setPosition(employee.position());
        existEmployee.setPhoneNumber(employee.phoneNumber());

        return existEmployee;
    }


}
