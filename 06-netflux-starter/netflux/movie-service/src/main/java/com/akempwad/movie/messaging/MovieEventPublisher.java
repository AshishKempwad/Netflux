package com.akempwad.movie.messaging;

import com.akempwad.netflux.events.MovieAddedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class MovieEventPublisher {

    private final Logger logger = LoggerFactory.getLogger(MovieEventPublisher.class);
    private final StreamBridge streamBridge;
    private final static String MOVIE_EVENTS_OUT = "movie-events-out";

    public MovieEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onMovieAdded(MovieAddedEvent movieAddedEvent) {
         var message = MessageBuilder.withPayload(movieAddedEvent)
                 .setHeader(KafkaHeaders.KEY,movieAddedEvent.movieId())
                 .build();

        this.streamBridge.send(MOVIE_EVENTS_OUT,message);
        logger.info("published: {}",message);
    }

}
