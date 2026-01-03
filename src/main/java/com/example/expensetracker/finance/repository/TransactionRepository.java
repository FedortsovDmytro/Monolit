package com.example.expensetracker.finance.repository;

import com.example.expensetracker.finance.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserId(Long userId);

    List<Transaction> findByUserIdAndDateBetween(
            Long userId,
            LocalDateTime start,
            LocalDateTime end
    );
}
