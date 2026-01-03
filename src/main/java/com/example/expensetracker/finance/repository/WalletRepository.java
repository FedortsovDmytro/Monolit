package com.example.expensetracker.finance.repository;

import com.example.expensetracker.finance.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository< Wallet,Long> {
}
