package com.vinsguru.playground.sec03.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
public class ConsumerConfig {

    private static final Logger log = LoggerFactory.getLogger(ConsumerConfig.class);

    @Bean
    public Consumer<String> consumer(){
        return msg -> log.info("received: {}", msg);
    }

    @Bean
    public Consumer<Message<String>> messageConsumer(){
        return this::handleMessage;
    }

    private void handleMessage(Message<String> message){
        log.info("message: {}", message);
        var key = message.getHeaders().get(KafkaHeaders.RECEIVED_KEY);
        var payload = message.getPayload();
        log.info("key: {}, payload: {}", key, payload);
    }

}
