package com.example.expensetracker.comunication.ConsumerProducer;

import com.example.expensetracker.comunication.event.TransactionCreatedEvent;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class TransactionJmsProducer {

    private final JmsTemplate jmsTemplate;

    public TransactionJmsProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendTransactionCreated(TransactionCreatedEvent event) {
        jmsTemplate.convertAndSend(
                "transaction.created",
                event
        );
    }
}
