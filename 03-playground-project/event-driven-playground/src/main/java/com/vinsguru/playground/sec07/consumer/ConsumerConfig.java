package com.vinsguru.playground.sec07.consumer;

import com.vinsguru.playground.sec07.dto.Notification;
import com.vinsguru.playground.sec07.dto.Payment;
import com.vinsguru.playground.sec07.dto.Shipment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ConsumerConfig {

    private static final Logger log = LoggerFactory.getLogger(ConsumerConfig.class);

    @Bean
    public Consumer<Payment> paymentConsumer() {
        return this::logReceived;
    }

    @Bean
    public Consumer<Shipment> shipmentConsumer() {
        return this::logReceived;
    }

    @Bean
    public Consumer<Notification> notificationConsumer() {
        return this::logReceived;
    }

    private void logReceived(Object payload) {
        log.info("received: {}", payload);
    }

}
