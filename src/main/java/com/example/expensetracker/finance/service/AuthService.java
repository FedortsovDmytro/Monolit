package com.example.expensetracker.finance.service;

import com.example.expensetracker.finance.dto.RegisterRequest;
import com.example.expensetracker.finance.entity.User;
import com.example.expensetracker.finance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public void registerUser(RegisterRequest req) {
        if(userRepository.findByEmail(req.getEmail()) != null){
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User.Builder().email(req.getEmail()).password(passwordEncoder.encode(req.getPassword())).build();
        userRepository.save(user);
    }
}
