package com.example.expensetracker.finance.repository;

import com.example.expensetracker.finance.entity.RefreshToken;
import com.example.expensetracker.finance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);

    void flush();
}
