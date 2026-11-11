package com.wd_aljak.employee_management.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record LeaveRequestCreateDTO(
        @NotBlank(message = "Reason is required")
        @Size(min = 2, max = 200, message = "min is 2, max is 200")
        String reason,

        @NotNull(message = "Start Date is required")
        @FutureOrPresent(message = "Date should be now or in the future")
        LocalDate startDate,

        @NotNull(message = "End Date is required")
        @FutureOrPresent(message = "Date should be now or in the future")
        LocalDate endDate

) {
}
