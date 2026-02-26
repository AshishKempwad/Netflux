package com.vinsguru.playground.sec19.producer;

import com.vinsguru.playground.sec19.dto.Order;
import com.vinsguru.playground.sec19.dto.ProductType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Configuration
public class ProducerConfig {

    private static final Logger log = LoggerFactory.getLogger(ProducerConfig.class);

    @Bean
    public Supplier<Message<Order>> producer() {
        var counter = new AtomicInteger(0);
        return () -> {
            var id = counter.incrementAndGet();
            var productType = id % 2 == 0 ? ProductType.PHYSICAL : ProductType.DIGITAL;
            var order = new Order(id, id, ThreadLocalRandom.current().nextInt(1, 1000), productType);
            log.info("produced: {}", order);
            return MessageBuilder.withPayload(order)
                                 .setHeader(KafkaHeaders.KEY, id)
                                 .build();
        };
    }

}
