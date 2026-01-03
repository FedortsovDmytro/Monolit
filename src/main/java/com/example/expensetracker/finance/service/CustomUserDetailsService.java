package com.example.expensetracker.finance.service;

import com.example.expensetracker.finance.entity.User;
import com.example.expensetracker.finance.repository.UserRepository;
import com.example.expensetracker.security.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        System.out.println("Trying to load user by email: " + email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));
        System.out.println("User found: " + user.getEmail());
        return new SecurityUser(user);
    }

}
