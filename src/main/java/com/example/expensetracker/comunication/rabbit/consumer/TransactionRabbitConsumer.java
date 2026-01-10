//package com.example.expensetracker.comunication.jms.consumer;
//
//import com.example.expensetracker.comunication.event.TransactionCreatedEvent;
//import com.example.expensetracker.comunication.jms.config.RabbitConfig;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.jms.annotation.JmsListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TransactionJmsConsumer {
//
//    private static final Logger log =
//            LoggerFactory.getLogger(TransactionJmsConsumer.class);
//
//    @JmsListener(destination = "transaction.created")
//    public void onTransactionCreated(TransactionCreatedEvent event) {
//
//        log.info("Received event: {}", event);
//
//        if (event.amount().doubleValue() > 100) {
//            throw new RuntimeException("Simulated processing error");
//        }
//
//        log.info("Processed successfully");
//    }
//    @JmsListener(destination = "ActiveMQ.DLQ")
//    public void onDlqMessage(TransactionCreatedEvent event) {
//
//        log.error(" DLQ message received: {}", event);}
//}
package com.example.expensetracker.comunication.jms.consumer;

import com.example.expensetracker.comunication.event.TransactionCreatedEvent;
import com.example.expensetracker.comunication.jms.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
        import org.springframework.stereotype.Component;
@Component
public class TransactionRabbitConsumer {

    private static final Logger log =
            LoggerFactory.getLogger(TransactionRabbitConsumer.class);

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void consume(TransactionCreatedEvent event) {

        log.info(" Received: {}", event);

        if (event.amount().doubleValue() > 100) {
            throw new RuntimeException("Simulated error → DLQ");
        }

        log.info(" Processed successfully");
    }

    @RabbitListener(queues = RabbitConfig.DLQ)
    public void consumeDlq(TransactionCreatedEvent event) {
        log.error("❌ DLQ MESSAGE: {}", event);
    }
}