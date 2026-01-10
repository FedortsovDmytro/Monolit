package com.example.expensetracker.comunication.jms.listener;

import com.example.expensetracker.comunication.event.TransactionCreatedEvent;
import com.example.expensetracker.comunication.jms.producer.TransactionRabbitProducer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class TransactionEventListener {

    private final TransactionRabbitProducer producer;

    public TransactionEventListener(TransactionRabbitProducer producer) {
        this.producer = producer;
    }

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(TransactionCreatedEvent event) {
        producer.send(event);
    }
}
