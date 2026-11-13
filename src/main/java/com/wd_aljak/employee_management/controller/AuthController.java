package com.wd_aljak.employee_management.controller;

import com.wd_aljak.employee_management.dtos.LoginRequest;
import com.wd_aljak.employee_management.dtos.SignupRequest;
import com.wd_aljak.employee_management.services.AuthService;
import com.wd_aljak.employee_management.shared.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<GlobalResponse<String>> login(
            @RequestBody @Valid LoginRequest loginRequest
    ) {
        String token = authService.login(loginRequest);

        return new ResponseEntity<>(
                new GlobalResponse<>(token), HttpStatus.CREATED);
    }

    @PostMapping("/signup")
    public ResponseEntity<GlobalResponse<String>> signup(
            @RequestBody @Valid SignupRequest signupRequest,
            @RequestParam String token
    ) {

        authService.signup(signupRequest, token);
        System.out.println(token);
        return new ResponseEntity<>(new GlobalResponse<>("Signed Up"), HttpStatus.CREATED);
    }
}