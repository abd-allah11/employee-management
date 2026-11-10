package com.wd_aljak.employee_management.services;

import com.wd_aljak.employee_management.abstracts.EmployeeService;
import com.wd_aljak.employee_management.dtos.EmployeeCreateDTO;
import com.wd_aljak.employee_management.dtos.EmployeeUpdateDTO;
import com.wd_aljak.employee_management.model.Department;
import com.wd_aljak.employee_management.model.Employee;
import com.wd_aljak.employee_management.repo.DepartmentRepo;
import com.wd_aljak.employee_management.repo.EmployeeRepo;
import com.wd_aljak.employee_management.shared.CustomResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    DepartmentRepo departmentRepo;

    @Override
    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    @Override
    public Employee findOne(UUID employeeId) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> CustomResponseException.ResponseNotFound("Employee with id -> " + employeeId + "Not found"));
        return employee;
    }

    @Override
    public Employee createOne(EmployeeCreateDTO employeeCreate) {
        Employee employee = new Employee();
        Department department = departmentRepo.findById(employeeCreate.departmentId())
                .orElseThrow(() -> CustomResponseException.ResponseNotFound(
                        "Employee with id -> " + employeeCreate.departmentId() + "Not found"));

        employee.setFirstName(employeeCreate.firstName());
        employee.setLastName(employeeCreate.lastName());
        employee.setHireDate(employeeCreate.hireDate());
        employee.setPhoneNumber(employeeCreate.phoneNumber());
        employee.setEmail(employeeCreate.email());
        employee.setPosition(employeeCreate.position());
        employee.setDepartment(department);
        employeeRepo.save(employee);
        return employee;
    }

    @Override
    public void deleteOne(UUID employeeId) {
        Optional<Employee> employee = employeeRepo.findById(employeeId);
        employee.ifPresent(value -> employeeRepo.deleteById(value.getId()));
    }

    @Override
    public Employee updateOne(UUID employeeId, EmployeeUpdateDTO employee) {
        Employee existEmployee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> CustomResponseException.ResponseNotFound("Employee with id -> " + employeeId + "Not found"));
        ;

        existEmployee.setFirstName(employee.firstName());
        existEmployee.setLastName(employee.lastName());
        existEmployee.setPosition(employee.position());
        existEmployee.setPhoneNumber(employee.phoneNumber());
        employeeRepo.save(existEmployee);

        return existEmployee;
    }


}
