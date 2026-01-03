package com.example.expensetracker.finance.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
//public record RegisterRequest(
//        @Email @NotBlank String email,
//        @Size(min = 8) String password
//) {}


public class RegisterRequest {

    @Email
    @NotBlank
    private final String email;

    @Size(min = 8)
    private final String password;

    public RegisterRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
