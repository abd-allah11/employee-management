package com.wd_aljak.employee_management.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomResponseException extends RuntimeException {
    private int code;
    private String message;

    public static CustomResponseException ResponseNotFound(String message) {
        return new CustomResponseException(404, message);
    }

    public static CustomResponseException BadCreadentials() {
        return new CustomResponseException(404, "Bad Creadentials");
    }

}
