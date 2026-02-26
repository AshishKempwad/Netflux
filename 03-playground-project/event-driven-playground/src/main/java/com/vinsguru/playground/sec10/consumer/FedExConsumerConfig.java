package com.vinsguru.playground.sec10.consumer;

import com.vinsguru.playground.sec10.dto.PhysicalDelivery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class FedExConsumerConfig {

    private static final Logger log = LoggerFactory.getLogger(FedExConsumerConfig.class);

    @Bean
    public Consumer<PhysicalDelivery> fedexConsumer() {
        return msg -> log.info("received: {}", msg);
    }

}
