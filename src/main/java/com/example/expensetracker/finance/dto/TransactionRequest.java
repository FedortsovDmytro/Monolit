package com.example.expensetracker.finance.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class TransactionRequest {

    public enum TransactionType {
        EXPENSE,
        INCOME
    }
    @NotNull
    private Long userId;

    @NotNull
    private Long walletId;

    @NotNull
    private Long categoryId;

    @NotNull
    @Positive
    private BigDecimal amount;

    @Size(max = 255)
    private String description;

    @NotNull
    private TransactionType type;
    public TransactionRequest() {}
    public TransactionRequest(Long userId, Long walletId, Long categoryId, BigDecimal amount, TransactionType type, String description) {
        this.userId = userId;
        this.walletId = walletId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.type = type;
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
