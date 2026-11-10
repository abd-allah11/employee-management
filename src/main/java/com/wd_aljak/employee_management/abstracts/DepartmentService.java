package com.wd_aljak.employee_management.abstracts;

import com.wd_aljak.employee_management.dtos.DepartmentDTO;
import com.wd_aljak.employee_management.model.Department;

import java.util.List;
import java.util.UUID;

public interface DepartmentService {

    List<Department> findAll();

    Department findOne(UUID departmentId);

    Department createOne(DepartmentDTO department);

    void deleteOne(UUID departmentId);

}
