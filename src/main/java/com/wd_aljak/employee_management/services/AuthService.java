package com.wd_aljak.employee_management.services;

import com.wd_aljak.employee_management.dtos.SingupRequest;
import com.wd_aljak.employee_management.model.Employee;
import com.wd_aljak.employee_management.model.UserAccount;
import com.wd_aljak.employee_management.repo.EmployeeRepo;
import com.wd_aljak.employee_management.repo.UserAccountRepo;
import com.wd_aljak.employee_management.shared.CustomResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private UserAccountRepo userAccountRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void singup(SingupRequest singupRequest) {
        Employee employee = employeeRepo.findById(singupRequest.employeeId())
                .orElseThrow(() ->
                        CustomResponseException.ResponseNotFound(
                                "Employee with id -> " + singupRequest.employeeId() + " Not found"
                        )
                );

        UserAccount account = new UserAccount();
        account.setUsername(singupRequest.username());
        account.setPassword(passwordEncoder.encode(singupRequest.password()));
        account.setEmployee(employee);
    }

}
