package com.wd_aljak.employee_management.services;

import com.wd_aljak.employee_management.abstracts.DepartmentService;
import com.wd_aljak.employee_management.dtos.DepartmentDTO;
import com.wd_aljak.employee_management.model.Department;
import com.wd_aljak.employee_management.repo.DepartmentRepo;
import com.wd_aljak.employee_management.shared.CustomResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentRepo departmentRepo;


    @Override
    public List<Department> findAll() {
        return departmentRepo.findAll();
    }

    @Override
    public Department findOne(UUID departmentId) {
        Department department = departmentRepo.findById(departmentId)
                .orElseThrow(() -> CustomResponseException.ResponseNotFound("Department with id -> " + departmentId + "Not found"));
        return department;
    }

    @Override
    public Department createOne(DepartmentDTO department) {
        Department newDepartment = new Department();

        newDepartment.setName(department.name());

        departmentRepo.save(newDepartment);
        return newDepartment;
    }

    @Override
    public void deleteOne(UUID departmentId) {
        Optional<Department> department = departmentRepo.findById(departmentId);
        department.ifPresent(value -> departmentRepo.deleteById(value.getId()));

    }
    
}
