package com.wd_aljak.employee_management.abstracts;

import com.wd_aljak.employee_management.dtos.EmployeeCreateDTO;
import com.wd_aljak.employee_management.dtos.EmployeeUpdateDTO;
import com.wd_aljak.employee_management.model.Employee;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface EmployeeService {

    Page<Employee> findAll(int page, int size);

    Employee findOne(UUID employeeId);

    Employee createOne(EmployeeCreateDTO employee);

    void deleteOne(UUID employeeId);

    Employee updateOne(UUID employeeId, EmployeeUpdateDTO employee);


}


