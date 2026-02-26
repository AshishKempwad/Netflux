package com.vinsguru.playground.sec02.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class ReactiveConsumerConfig {

    private static final Logger log = LoggerFactory.getLogger(ReactiveConsumerConfig.class);

    @Bean
    public Function<Flux<String>, Mono<Void>> reactiveConsumer() {
        return flux -> flux.doOnNext(msg -> log.info("received: {}", msg))
                           .then();
    }

}
