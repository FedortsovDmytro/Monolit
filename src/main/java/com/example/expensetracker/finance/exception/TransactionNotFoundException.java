package com.example.expensetracker.finance.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(Long Id) {
        super("Transaction not found with id: " + Id);
    }
}
