package com.vinsguru.playground.sec02;

import com.vinsguru.playground.sec19.SectionRunner;
import com.vinsguru.playground.sec19.dto.Order;
import com.vinsguru.playground.sec19.dto.ProductType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@SpringBootTest(
        classes = SectionRunner.Producer.class,
        properties = {
                "section=sec19",
                "config=04-producer",
                "spring.cloud.function.definition=producer;testConsumer",
                "spring.cloud.stream.bindings.testConsumer-in-0.destination=order-events",
                "spring.cloud.stream.kafka.binder.consumer-properties.key.deserializer=org.apache.kafka.common.serialization.IntegerDeserializer",
                "spring.cloud.stream.kafka.binder.consumer-properties.auto.offset.reset=earliest"
        }
)
@Import(Lec03ProducerTest.TestConsumerConfiguration.class)
class Lec03ProducerTest extends AbstractTest {

    @Autowired
    private BlockingQueue<Message<Order>> orders;

    @Test
    public void orderProducer() throws InterruptedException {
        var message1 = orders.poll(5, TimeUnit.SECONDS);
        var message2 = orders.poll(5, TimeUnit.SECONDS);

        // we should use receivedKey. We use kafka binder and our test is a consumer.
        Assertions.assertNotNull(message1);
        Assertions.assertEquals(1, message1.getHeaders().get(KafkaHeaders.RECEIVED_KEY, Integer.class));
        Assertions.assertEquals(1, message1.getPayload().id());
        Assertions.assertEquals(ProductType.DIGITAL, message1.getPayload().productType());

        Assertions.assertNotNull(message2);
        Assertions.assertEquals(2, message2.getHeaders().get(KafkaHeaders.RECEIVED_KEY, Integer.class));
        Assertions.assertEquals(2, message2.getPayload().id());
        Assertions.assertEquals(ProductType.PHYSICAL, message2.getPayload().productType());
    }

    @TestConfiguration
    static class TestConsumerConfiguration {

        @Bean
        public BlockingQueue<Message<Order>> orders(){
            return new LinkedBlockingQueue<>(); // thread safe
        }

        @Bean
        public Consumer<Message<Order>> testConsumer(BlockingQueue<Message<Order>> orders){
            return  orders::add;
        }

    }

}
