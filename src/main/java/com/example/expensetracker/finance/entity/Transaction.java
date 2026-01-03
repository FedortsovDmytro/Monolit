package com.example.expensetracker.finance.entity;

import com.example.expensetracker.finance.dto.TransactionRequest;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    public enum Type {
        EXPENSE,
        INCOME
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    private TransactionRequest.TransactionType type;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Transaction() {
    }

    private Transaction(Builder builder) {
        this.amount = builder.amount;
        this.date = builder.date;
        this.user = builder.user;
        this.category = builder.category;
        this.wallet = builder.wallet;
        this.description = builder.description;
        this.type = builder.type;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    public static class Builder {
        private final BigDecimal amount;
        private final LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private final User user;
        private final Category category;
        private final Wallet wallet;
        private String description;
        private final TransactionRequest.TransactionType type;
        private final LocalDateTime date;

        public Builder(BigDecimal amount, User user, Category category, Wallet wallet, TransactionRequest.TransactionType type, LocalDateTime date) {
            this.amount = amount;
            this.user = user;
            this.category = category;
            this.wallet = wallet;
            this.type = type;
            this.date = date;
            this.createdAt = date;
            this.updatedAt = date;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }


        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }

    public Long getId() { return id; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getDate() { return date; }
    public User getUser() { return user; }
    public Category getCategory() { return category; }
    public Wallet getWallet() { return wallet; }
    public String getDescription() { return description; }
    public TransactionRequest.TransactionType getType() { return type; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", date=" + date +
                ", type=" + type +
                '}';
    }
}
