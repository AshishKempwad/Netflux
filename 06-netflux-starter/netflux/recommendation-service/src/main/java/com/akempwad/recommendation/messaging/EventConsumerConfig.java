package com.akempwad.recommendation.messaging;

import com.akempwad.netflux.events.CustomerGenreUpdatedEvent;
import com.akempwad.netflux.events.MovieAddedEvent;
import com.akempwad.recommendation.service.CustomerService;
import com.akempwad.recommendation.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class EventConsumerConfig {

    private static final Logger log = LoggerFactory.getLogger(EventConsumerConfig.class);

    @Bean
    public Consumer<CustomerGenreUpdatedEvent> genreUpdatedEventConsumer(CustomerService customerService){
        return withLogging(event -> customerService.updateGenre(event));
    }

    @Bean
    public Consumer<MovieAddedEvent> movieAddedEventConsumer(MovieService movieService){
        return withLogging(event -> movieService.addMovie(event));
    }

    private <T> Consumer<T> withLogging(Consumer<T> consumer){
        return t ->{
            log.info("received: {}",t);
            consumer.accept(t);
        };
    }
}
