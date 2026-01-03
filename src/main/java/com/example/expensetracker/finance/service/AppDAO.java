package com.example.expensetracker.finance.service;

import com.example.expensetracker.config.AppProperties;
import com.example.expensetracker.finance.dto.TransactionRequest;
import com.example.expensetracker.finance.entity.Category;
import com.example.expensetracker.finance.entity.Transaction;
import com.example.expensetracker.finance.entity.User;
import com.example.expensetracker.finance.entity.Wallet;
import com.example.expensetracker.finance.exception.*;
import com.example.expensetracker.finance.repository.CategoryRepository;
import com.example.expensetracker.finance.repository.TransactionRepository;
import com.example.expensetracker.finance.repository.UserRepository;
import com.example.expensetracker.finance.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class AppDAO {

    private final CategoryRepository categoryRepo;
    private final TransactionRepository txRepo;
    private final UserRepository userRepo;
    private final WalletRepository walletRepo;
    private final AppProperties appProperties;
    public AppDAO(CategoryRepository categoryRepo,
                  TransactionRepository txRepo,
                  UserRepository userRepo,
                  WalletRepository walletRepo,
                  AppProperties appProperties) {

        this.categoryRepo = categoryRepo;
        this.txRepo = txRepo;
        this.userRepo = userRepo;
        this.walletRepo = walletRepo;
        this.appProperties = appProperties;
    }

    // ---------------- CATEGORY ----------------

    @Transactional
    public Category createCategory(String name, String description, Category.CategoryType type) {
        Category category = new Category.Builder(name, type)
                .setCategoryDescription(description)
                .build();
        return categoryRepo.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepo.findById(id)
            .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Transactional
    public void deleteCategoryById(Long id) {
        categoryRepo.deleteById(id);
    }

    // ---------------- USER ----------------

    @Transactional
    public User createUser(String name, String email, String password) {
        User user = new User.Builder()
                .fullName(name)
                .email(email)
                .password(password)
                .createdAt(LocalDateTime.now())
                .build();

        return userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User findUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }


    public User findUserByEmail(String email) {
        return userRepo.findAll()
                .stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }


    @Transactional
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    // ---------------- WALLET ----------------

    @Transactional
    public Wallet createWallet(Long userId, String name, Wallet.Currency currency) {
        User user = findUserById(userId);

        Wallet wallet = new Wallet();
        wallet.setName(name);
        wallet.setCurrency(currency);
        wallet.setUser(user);

        return walletRepo.save(wallet);
    }

    public Set<Wallet> getAllWalletsByUserId(Long id) {
        return findUserById(id).getWallets();
    }

    public Wallet findWalletById(Long id) {
        return walletRepo.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(id));
    }

    @Transactional
    public void deleteWalletById(Long id) {
        walletRepo.deleteById(id);
    }

    // ---------------- TRANSACTION ----------------

    @Transactional
    public Transaction createTransaction(TransactionRequest req) {

        User user = findUserById(req.getUserId());
        Wallet wallet = findWalletById(req.getWalletId());
        Category category = getCategoryById(req.getCategoryId());



        LocalDateTime now = LocalDateTime.now();

        Transaction tx = new Transaction.Builder(
                req.getAmount(),
                user,
                category,
                wallet,
                req.getType(),
                now
        )
                .description(req.getDescription())
                .build();

        if (tx.getType() == TransactionRequest.TransactionType.EXPENSE) {
            if (wallet.getBalance().compareTo(tx.getAmount()) < 0) {
                throw new InsufficientFundsException(wallet.getBalance(),tx.getAmount());
            }
            wallet.setBalance(wallet.getBalance().subtract(tx.getAmount()));
        } else {
            wallet.setBalance(wallet.getBalance().add(tx.getAmount()));
        }

        txRepo.save(tx);
        walletRepo.save(wallet);

        return tx;
    }
    public List<Transaction> getAllTransactionsByUserId(Long id,Integer size)
    {
        int finalSize=size!=null?size:appProperties.getPagination().getDefaultSize();
        return txRepo.findByUserId(id).stream().limit(finalSize).toList();
    }


    public Transaction findTransactionById(Long id) {
        return txRepo.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
    }

    @Transactional
    public void deleteTransactionById(Long id) {
        txRepo.deleteById(id);
    }

    public List<Transaction> getTransactionsByDateRange(
            Long id,
            LocalDateTime start,
            LocalDateTime end) {

        return txRepo.findByUserIdAndDateBetween(id, start, end);
    }

}
