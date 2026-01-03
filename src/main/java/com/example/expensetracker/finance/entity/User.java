package com.example.expensetracker.finance.entity;

import jakarta.persistence.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
enum Role {USER, ADMIN}
    public enum MainAdmin {
        INSTANCE;

        private final String fullName = "Admin";
        private final String email = "fedortsovdmytro@gmail.com";
        private final String password = "password123";

        public String getFullName() {
            return fullName;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private Role role;
    public User(String email, String s, List<SimpleGrantedAuthority> authorities) {}

    public User() {}
    private User(Builder builder) {
        this.fullName = builder.fullName;
        this.email = builder.email;
        this.password = builder.password;
        this.createdAt = builder.createdAt;
        this.role = builder.role;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }

    public static class Builder {
        private String fullName;
        private String email;
        private String password;
        private LocalDateTime createdAt;
        private Role role;
        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        public Builder role(Role role) {
            this.role = role;
            return this;
        }
        public User build() {
            return new User(this);
        }
    }
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Wallet> wallets;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Set<Transaction> transactions;

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public Set<Wallet> getWallets() {
        return wallets;
    }
    /// //////////////////2FA
    private boolean twoFactorAuthenticationEnabled;
    public boolean isTwoFactorAuthenticationEnabled() {
        return twoFactorAuthenticationEnabled;
    }
    @Column(length=64)
    private String twoFactorSecret;

    public String getTwoFactorSecret() {
        return twoFactorSecret;
    }

    public void setTwoFactorSecret(String twoFactorSecret) {
        this.twoFactorSecret = twoFactorSecret;
    }

    public void setTwoFactorAuthenticationEnabled(boolean twoFactorAuthenticationEnabled) {
        this.twoFactorAuthenticationEnabled = twoFactorAuthenticationEnabled;
    }
}
