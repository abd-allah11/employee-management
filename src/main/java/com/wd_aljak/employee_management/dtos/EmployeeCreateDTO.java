package com.wd_aljak.employee_management.dtos;

import jakarta.validation.constraints.*;

import java.util.Date;

public record EmployeeCreateDTO(
@NotBlank(message = "first name is required")
@Size(min = 2, max = 20, message = "min is 2 ,max is 20")
String firstName,

@NotBlank(message = "last name is required")
@Size(min = 2, max = 20, message = "min is 2 ,max is 20")
String lastName,

@NotBlank(message = "email is required")
@Email
String email,

@NotBlank(message = "phoneNumber is required")
@Pattern(regexp = "^[0-9]{10}$", message = "phoneNumber must be exactly 10 digits")
String phoneNumber,

@NotNull(message = "hireDate is required")
@PastOrPresent(message = "cant be in future")
Date hireDate,

@NotBlank(message = "position is required")
@Size(min = 2, max = 20, message = "min is 2 ,max is 20")
String position
) {
}
