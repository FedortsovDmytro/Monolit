package com.example.expensetracker.comunication.event;

import com.example.expensetracker.finance.dto.TransactionRequest;

import java.math.BigDecimal;

public record TransactionCreatedEvent(
        Long transactionId,
        TransactionRequest.TransactionType type,
        BigDecimal amount
) {}
