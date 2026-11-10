package com.wd_aljak.employee_management.abstracts;

import com.wd_aljak.employee_management.dtos.EmployeeCreateDTO;
import com.wd_aljak.employee_management.dtos.EmployeeUpdateDTO;
import com.wd_aljak.employee_management.model.Employee;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    List<Employee> findAll();

    Employee findOne(UUID employeeId);

    Employee createOne(EmployeeCreateDTO employee);

    void deleteOne(UUID employeeId);

    Employee updateOne(UUID employeeId, EmployeeUpdateDTO employee);


}


