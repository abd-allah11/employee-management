package com.wd_aljak.employee_management.controller;

import com.wd_aljak.employee_management.dtos.SingupRequest;
import com.wd_aljak.employee_management.services.AuthService;
import com.wd_aljak.employee_management.shared.GlobalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
 
    @PostMapping("/singup")
    public ResponseEntity<GlobalResponse<String>> singup(
            @RequestBody SingupRequest singupRequest) {
        authService.singup(singupRequest);
        return new ResponseEntity<>(new GlobalResponse<>("singin"), HttpStatus.CREATED);
    }
}
