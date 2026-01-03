package com.example.expensetracker.finance.repository;

import com.example.expensetracker.finance.entity.User;
import com.example.expensetracker.security.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

}
