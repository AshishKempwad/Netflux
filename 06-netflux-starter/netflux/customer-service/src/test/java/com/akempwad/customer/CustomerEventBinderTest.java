package com.akempwad.customer;

import com.akempwad.customer.dto.GenreUpdateRequest;
import com.akempwad.customer.repository.CustomerRepository;
import com.akempwad.netflux.events.CustomerGenreUpdatedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.stream.binder.test.EnableTestBinder;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.json.JsonMapper;

import java.time.Instant;

@SpringBootTest
@EnableTestBinder
@AutoConfigureRestTestClient
public class CustomerEventBinderTest {

    @Autowired
    private RestTestClient testClient;

    @Autowired
    private OutputDestination outputDestination;

    @Autowired
    private TransactionalEventProbe transactionalEventProbe;

    @Test
    public void genreUpdatedEvent() {
        var request = new GenreUpdateRequest("Thriller");
        this.testClient.patch()
                .uri("/api/customers/2/genre")
                .body(request)
                .exchange()
                .expectStatus().is2xxSuccessful();

        var message = this.outputDestination.receive(1000,"customer-events");
        var event = JsonMapper.shared().readValue(message.getPayload(), CustomerGenreUpdatedEvent.class);
        Assertions.assertEquals(2,message.getHeaders().get(KafkaHeaders.KEY,Integer.class));
        Assertions.assertEquals(2,event.customerId());
        Assertions.assertEquals("Thriller",event.favoriteGenre());

    }

    @Test
    public void noEventWhenTransactionRollsBack() {
        Assertions.assertThrows(IllegalStateException.class, () -> this.transactionalEventProbe.publishThenRollback(99, "Sci-Fi"));
        var message = this.outputDestination.receive(200, "customer-events");
        Assertions.assertNull(message);
    }

    @TestConfiguration
    static class TransactionalEventProbeConfiguration {

        @Bean
        TransactionalEventProbe transactionalEventProbe(ApplicationEventPublisher applicationEventPublisher,
                                                        CustomerRepository customerRepository) {
            return new TransactionalEventProbe(applicationEventPublisher, customerRepository);
        }
    }

    static class TransactionalEventProbe {

        private final ApplicationEventPublisher applicationEventPublisher;
        private final CustomerRepository customerRepository;

        TransactionalEventProbe(ApplicationEventPublisher applicationEventPublisher, CustomerRepository customerRepository) {
            this.applicationEventPublisher = applicationEventPublisher;
            this.customerRepository = customerRepository;
        }

        @Transactional
        public void publishThenRollback(Integer customerId, String genre) {
            this.customerRepository.findById(1);
            this.applicationEventPublisher.publishEvent(new CustomerGenreUpdatedEvent(customerId, genre, Instant.now()));
            throw new IllegalStateException("Forcing rollback");
        }
    }

}
