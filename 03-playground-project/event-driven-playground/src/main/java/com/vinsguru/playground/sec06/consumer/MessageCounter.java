package com.vinsguru.playground.sec06.consumer;

import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MessageCounter {

    private static final Logger log = LoggerFactory.getLogger(MessageCounter.class);
    private final AtomicInteger counter = new AtomicInteger();

    public void increment() {
        counter.incrementAndGet();
    }

    @PreDestroy
    public void onShutdown() {
        log.info("Total messages consumed: {}", counter.get());
    }

}
