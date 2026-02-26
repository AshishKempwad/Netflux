package com.vinsguru.playground.sec10.consumer;

import com.vinsguru.playground.sec10.dto.DigitalDelivery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class DigitalDeliveryConsumerConfig {

    private static final Logger log = LoggerFactory.getLogger(DigitalDeliveryConsumerConfig.class);

    @Bean
    public Consumer<DigitalDelivery> digitalConsumer() {
        return msg -> log.info("received: {}", msg);
    }

}
