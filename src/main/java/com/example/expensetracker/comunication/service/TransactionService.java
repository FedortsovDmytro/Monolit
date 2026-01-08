package com.example.expensetracker.comunication.service;

import com.example.expensetracker.comunication.event.TransactionCreatedEvent;
import com.example.expensetracker.finance.entity.Transaction;
import com.example.expensetracker.finance.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository repository;
    private final ApplicationEventPublisher publisher;

    public TransactionService(TransactionRepository repository,
                              ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Transactional
    public Transaction create(Transaction transaction) {

        Transaction saved = repository.save(transaction);

        publisher.publishEvent(
                new TransactionCreatedEvent(
                        saved.getId(),
                        saved.getType(),
                        saved.getAmount()
                )
        );

        return saved;
    }
}
