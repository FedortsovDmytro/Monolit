package com.example.expensetracker.finance.mapper;

import com.example.expensetracker.finance.dto.TransactionResponse;
import com.example.expensetracker.finance.entity.Transaction;

public class TransactionMapper {

    public static TransactionResponse toResponse(Transaction tx) {
        TransactionResponse dto = new TransactionResponse();
        dto.setId(tx.getId());
        dto.setAmount(tx.getAmount());
        dto.setDescription(tx.getDescription());
        dto.setType(tx.getType().name());
        dto.setDate(tx.getDate());
        dto.setCategoryId(tx.getCategory().getId());
        dto.setWalletId(tx.getWallet().getId());
        return dto;
    }
}
