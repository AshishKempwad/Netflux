package com.vinsguru.recommendation;

import com.vinsguru.netflux.events.CustomerGenreUpdatedEvent;
import com.vinsguru.recommendation.dto.MovieRecommendations;
import com.vinsguru.recommendation.dto.RecommendationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.EnableTestBinder;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.client.RestTestClient;
import tools.jackson.databind.json.JsonMapper;

import java.time.Instant;
import java.util.List;

@SpringBootTest
@EnableTestBinder
@AutoConfigureRestTestClient
@Sql(value = "classpath:test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class RecommendationServiceApiTest {

    @Autowired
    private RestTestClient testClient;

    @Autowired
    private InputDestination inputDestination;

    @Test
    public void recommendations() {
        var recommendations = this.testClient.get()
                                             .uri("/api/recommendations/1")
                                             .exchange()
                                             .expectStatus().is2xxSuccessful()
                                             .returnResult(new ParameterizedTypeReference<List<MovieRecommendations>>() {
                                             }).getResponseBody();

        Assertions.assertNotNull(recommendations);
        Assertions.assertEquals(2, recommendations.size());

        Assertions.assertEquals(RecommendationType.NEWLY_ADDED, recommendations.getFirst().type());
        Assertions.assertEquals(9, recommendations.getFirst().movies().size());

        Assertions.assertEquals(RecommendationType.PERSONALIZED, recommendations.getLast().type());
        Assertions.assertEquals(4, recommendations.getLast().movies().size());
    }

    @Test // prefer WebFlux / WebTestClient for streaming APIs
    public void recommendationsStream() throws InterruptedException {
        var event = new CustomerGenreUpdatedEvent(2, "Sci-Fi", Instant.now());
        this.inputDestination.send(MessageBuilder.withPayload(event).build(), "customer-events");
        Thread.sleep(100); // wait for async processing to publish the event
        var response = this.testClient.get()
                                      .uri("/api/recommendations/2/stream")
                                      .exchange()
                                      .expectStatus().is2xxSuccessful()
                                      .returnResult(String.class) // No suitable HTTP message converter found
                                      .getResponseBody();
        Assertions.assertNotNull(response);
        // SSE responses use the format "data: <payload>"
        // remove the "data:" prefix before JSON deserialization using substring(5)
        var recommendation = JsonMapper.shared().readValue(response.substring(5), MovieRecommendations.class);
        Assertions.assertEquals(RecommendationType.PERSONALIZED, recommendation.type());
        Assertions.assertEquals(2, recommendation.customerId());
        Assertions.assertEquals(4, recommendation.movies().size());
    }

}
