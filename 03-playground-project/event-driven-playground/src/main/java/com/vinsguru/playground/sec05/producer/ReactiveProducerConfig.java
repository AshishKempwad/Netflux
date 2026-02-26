package com.vinsguru.playground.sec05.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.function.Supplier;

@Configuration
public class ReactiveProducerConfig {

    private static final Logger log = LoggerFactory.getLogger(ReactiveProducerConfig.class);

    @Bean
    public Supplier<Flux<String>> reactiveProducer() {
        return () -> Flux.interval(Duration.ofMillis(500))
                         .map(i -> "msg-" + i)
                         .doOnNext(msg -> log.info("sending: {}", msg));
    }

}
