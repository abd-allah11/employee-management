package com.wd_aljak.employee_management.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DepartmentDTO(
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 20, message = "min is 2 ,max is 20")
        String name
) {
}
