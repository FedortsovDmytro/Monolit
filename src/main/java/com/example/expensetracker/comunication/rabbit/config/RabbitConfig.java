package com.example.expensetracker.comunication.jms.config;

//import ch.qos.logback.classic.pattern.MessageConverter;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
//
//import java.lang.runtime.ObjectMethods;
//
//@Configuration
//public class jmsConfig {
//
//    @Bean
//    public MappingJackson2MessageConverter jmsMessageConverter(ObjectMapper  objectMapper) {
//        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//        converter.setObjectMapper(objectMapper);
//        converter.setTargetType(org.springframework.jms.support.converter.MessageType.TEXT);
//        converter.setTypeIdPropertyName("_type");
//        return converter;
//
//    }
//}

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String EXCHANGE = "transaction.exchange";
    public static final String QUEUE = "transaction.created";
    public static final String DLQ = "transaction.created.dlq";
    @Bean
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }
    @Bean
    Queue queue() {
        return  QueueBuilder.durable(QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", DLQ)
                .build();
    }
    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(DLQ);
    }
    @Bean
    public MessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter();
    }

}