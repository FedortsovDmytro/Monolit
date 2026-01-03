package com.example.expensetracker.finance.exception;

public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException(Long id) {
        super("Wallet not found with id: " + id);
    }
}
