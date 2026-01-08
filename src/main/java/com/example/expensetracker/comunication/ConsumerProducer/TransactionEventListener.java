package com.example.expensetracker.comunication.ConsumerProducer;

import com.example.expensetracker.comunication.event.TransactionCreatedEvent;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class TransactionEventListener {

    private final TransactionJmsProducer producer;

    public TransactionEventListener(TransactionJmsProducer producer) {
        this.producer = producer;
    }

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(TransactionCreatedEvent event) {

        producer.sendTransactionCreated(event);
    }
}
