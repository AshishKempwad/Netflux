package com.vinsguru.recommendation;

import com.vinsguru.netflux.events.CustomerGenreUpdatedEvent;
import com.vinsguru.netflux.events.MovieAddedEvent;
import com.vinsguru.recommendation.repository.CustomerGenreRepository;
import com.vinsguru.recommendation.repository.MovieRepository;
import org.awaitility.Awaitility;
import org.awaitility.core.ThrowingRunnable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.MessageBuilder;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
public class RecommendationServiceKafkaTest {

    @Autowired
    private StreamBridge streamBridge;

    @Autowired
    private CustomerGenreRepository customerGenreRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void customerGenreUpdatedEvent() {
        var event = new CustomerGenreUpdatedEvent(1, "Action", Instant.now());
        this.streamBridge.send("customer-events", event);
        this.waitUntilAsserted(() -> {
            var customerGenre = this.customerGenreRepository.findById(1).orElseThrow(Assertions::fail);
            Assertions.assertEquals("Action", customerGenre.getFavoriteGenre());
        });
    }

    @Test
    public void movieAddedEvent() {
        var event = new MovieAddedEvent(1, "Inception", 100, 110, null, List.of("Action"), null, Instant.now());
        this.streamBridge.send("movie-events", event);
        this.waitUntilAsserted(() -> {
            var movie = this.movieRepository.findById(1).orElseThrow();
            Assertions.assertEquals("Inception", movie.getTitle());
        });
    }

    private void waitUntilAsserted(ThrowingRunnable runnable){
        Awaitility.await()
                .atMost(Duration.ofSeconds(10))
                .untilAsserted(runnable);
    }

}
