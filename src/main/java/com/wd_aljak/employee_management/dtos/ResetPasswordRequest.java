package com.wd_aljak.employee_management.dtos;

public record ResetPasswordRequest(
        String token,
        String newPassword
) {
}
