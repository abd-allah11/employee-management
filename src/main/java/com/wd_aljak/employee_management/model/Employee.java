package com.wd_aljak.employee_management.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Employee {
    private UUID id;
    @NotBlank(message = "first name is required")
    @Size(min = 2, max = 20, message = "min is 2 ,max is 20")
    private String firstName;

    @NotBlank(message = "last name is required")
    @Size(min = 2, max = 20, message = "min is 2 ,max is 20")
    private String lastName;

    @NotBlank(message = "email is required")
    @Email
    private String email;

    @NotBlank(message = "phoneNumber is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "phoneNumber must be exactly 10 digits")
    private String phoneNumber;

    @NotNull(message = "hireDate is required")
//    @PastOrPresent(message = "cant be in future")
    private Date hireDate;

    @NotBlank(message = "position is required")
    @Size(min = 2, max = 20, message = "min is 2 ,max is 20")
    private String position;
    private UUID departmentId;

}
