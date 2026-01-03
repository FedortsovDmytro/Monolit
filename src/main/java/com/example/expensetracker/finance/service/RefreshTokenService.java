package com.example.expensetracker.finance.service;


import com.example.expensetracker.finance.entity.RefreshToken;
import com.example.expensetracker.finance.entity.User;
import com.example.expensetracker.finance.repository.RefreshTokenRepository;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
@Service
public class RefreshTokenService {

    private static final Duration TTL = Duration.ofHours(5);
    private final RefreshTokenRepository repo;

    public RefreshTokenService(RefreshTokenRepository repo) {
        this.repo = repo;
    }

    public RefreshToken create(User user) {
        repo.deleteByUser(user);

        String token = UUID.randomUUID().toString();

        return repo.save(
                new RefreshToken(
                        user,
                        token,
                        Instant.now().plus(TTL)
                )
        );
    }

    public RefreshToken validate(String token) {
        RefreshToken rt = repo.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        if (rt.isExpired()) {
            repo.delete(rt);
            throw new IllegalArgumentException("Refresh token expired");
        }
        return rt;
    }
    public void revoke(String token) {

        repo.findByToken(token).ifPresent(repo::delete);
    }
}
