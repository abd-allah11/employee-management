package com.wd_aljak.employee_management.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record SignupRequest(
        @NotBlank(message = "Username is required")
        @Size(min = 2, max = 20, message = "min is 2 ,max is 20")
        String username,
        @NotBlank(message = "Password is required")
        @Size(min = 2, max = 20, message = "min is 2 ,max is 20")
        String password,
        @NotNull(message = "Password is required")
        UUID employeeId
) {
}
