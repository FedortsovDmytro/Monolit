package com.example.expensetracker.finance.dto;

public record TwoFactorLoginRequest(       Long userId,
                                           String code) {

}
