package com.vinsguru.playground.sec03.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Configuration
public class ProducerConfig {

    private static final Logger log = LoggerFactory.getLogger(ProducerConfig.class);

    @Bean
    public Supplier<String> producer() {
        var counter = new AtomicInteger(0);
        return () -> {
            var msg = "msg-" + counter.incrementAndGet();
            log.info("produced: {}", msg);
            return msg;
        };
    }

    @Bean
    public Supplier<Message<String>> messageProducer() {
        var counter = new AtomicInteger(0);
        return () -> {
            var msg = this.buildMessage(counter.incrementAndGet());
            log.info("produced: {}", msg);
            return msg;
        };
    }

    private Message<String> buildMessage(Integer input) {
        return MessageBuilder.withPayload("msg-" + input)
                             .setHeader(KafkaHeaders.KEY, "key-" + input)
                             .setHeader("trace-id", "trace-" + input)
                             .build();
    }

}
