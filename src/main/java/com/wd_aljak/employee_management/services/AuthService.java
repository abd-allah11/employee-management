package com.wd_aljak.employee_management.services;

import com.wd_aljak.employee_management.config.JwtHelper;
import com.wd_aljak.employee_management.dtos.LoginRequest;
import com.wd_aljak.employee_management.dtos.SignupRequest;
import com.wd_aljak.employee_management.model.Employee;
import com.wd_aljak.employee_management.model.UserAccount;
import com.wd_aljak.employee_management.repo.EmployeeRepo;
import com.wd_aljak.employee_management.repo.UserAccountRepo;
import com.wd_aljak.employee_management.shared.CustomResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserAccountRepo userAccountRepo;
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtHelper jwtHelper;

    public void signup(SignupRequest signupRequest, String token) {
        Employee employee = employeeRepo.findOneByAccountCreationToken(token)
                .orElseThrow(() -> CustomResponseException.ResourceNotFound("Invalid token"));

        if (employee.isVerified()) {
            throw CustomResponseException.BadRequest("Account already created");
        }

        UserAccount account = new UserAccount();
        account.setUsername(signupRequest.username());
        account.setPassword(passwordEncoder.encode(signupRequest.password()));
        account.setEmployee(employee);
        userAccountRepo.save(account);

        employee.setVerified(true);
        employee.setAccountCreationToken(null);
        employeeRepo.save(employee);
    }


    public String login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        UserAccount user = userAccountRepo.findOneByUsername(loginRequest.username())
                .orElseThrow(() -> CustomResponseException.BadCredentials());

        Map<String, Object> customClaims = new HashMap<>();
        customClaims.put("userId", user.getId());
        customClaims.put("role", user.getRole());
        return jwtHelper.generateToken(customClaims, user);
    }

}
