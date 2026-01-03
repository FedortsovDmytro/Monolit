package com.example.expensetracker.finance.service;

import org.springframework.stereotype.Service;

@Service
public class QrCodeService {
public String generateQrCode(String email,String secret){
    return String.format(
            "otpauth://totp/ExpenseTracker:%s?secret=%s&issuer=ExpenseTracker",
            secret,
            email
    );
}
}
