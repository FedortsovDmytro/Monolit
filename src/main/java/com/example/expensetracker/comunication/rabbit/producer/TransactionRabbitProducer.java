package com.example.expensetracker.comunication.jms.producer;
//
//import com.example.expensetracker.comunication.event.TransactionCreatedEvent;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TransactionJmsProducer {
//
//    private final JmsTemplate jmsTemplate;
//
//    public TransactionJmsProducer(JmsTemplate jmsTemplate) {
//        this.jmsTemplate = jmsTemplate;
//    }
//
//    public void sendTransactionCreated(TransactionCreatedEvent event) {
//        jmsTemplate.convertAndSend(
//                "transaction.created",
//                event
//        );
//    }
//}

import com.example.expensetracker.comunication.event.TransactionCreatedEvent;
import com.example.expensetracker.comunication.jms.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransactionRabbitProducer {

    private final RabbitTemplate rabbitTemplate;
    public TransactionRabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public void send(TransactionCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.QUEUE,
                event
        );
    }
}