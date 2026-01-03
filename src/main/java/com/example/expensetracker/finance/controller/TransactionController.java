package com.example.expensetracker.finance.controller;

import com.example.expensetracker.finance.dto.TransactionRequest;
import com.example.expensetracker.finance.dto.TransactionResponse;
import com.example.expensetracker.finance.entity.Transaction;
import com.example.expensetracker.finance.mapper.TransactionMapper;
import com.example.expensetracker.finance.service.AppDAO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final AppDAO service;

    public TransactionController(AppDAO service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public TransactionResponse create(
            @Valid @RequestBody TransactionRequest request
    ) {
        Transaction tx = service.createTransaction(request);
        return TransactionMapper.toResponse(tx);
    }
}
