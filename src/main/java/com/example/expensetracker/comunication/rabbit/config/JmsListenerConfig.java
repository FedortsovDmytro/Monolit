//package com.example.expensetracker.comunication.jms.config;
//
//import jakarta.jms.ConnectionFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
//import org.springframework.util.backoff.FixedBackOff;
//
//@Configuration
//public class JmsListenerConfig {
//@Bean
//    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
//    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//    factory.setConnectionFactory(connectionFactory);
//    factory.setBackOff(
//            new FixedBackOff(
//                    2000L,
//                    3
//            )
//    );
//    return factory;
//
//}
//
//}
