package com.vinsguru.playground.sec02;

import com.vinsguru.playground.sec19.SectionRunner;
import com.vinsguru.playground.sec19.dto.DigitalDelivery;
import com.vinsguru.playground.sec19.dto.Order;
import com.vinsguru.playground.sec19.dto.PhysicalDelivery;
import com.vinsguru.playground.sec19.dto.ProductType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@SpringBootTest(
        classes = SectionRunner.Processor.class,
        properties = {
                "section=sec19",
                "config=03-processor",
                "spring.cloud.function.definition=deliveryProcessor;digitalConsumer;physicalConsumer",
                "spring.cloud.stream.bindings.digitalConsumer-in-0.destination=digital-delivery",
                "spring.cloud.stream.bindings.physicalConsumer-in-0.destination=physical-delivery",
                "spring.cloud.stream.kafka.binder.consumer-properties.auto.offset.reset=earliest"
        }
)
@Import(Lec04ProcessorTest.TestConsumerConfiguration.class)
class Lec04ProcessorTest extends AbstractTest {

    @Autowired
    private BlockingQueue<Message<DigitalDelivery>> digitalDeliveryQueue;

    @Autowired
    private BlockingQueue<Message<PhysicalDelivery>> physicalDeliveryQueue;

    @Test
    public void digitalOrder() throws InterruptedException {
        // produce a digital order via order-events
        var digitalOrder = new Order(1, 1, 1, ProductType.DIGITAL);
        var inputMessage = MessageBuilder.withPayload(digitalOrder).build();
        this.streamBridge.send(ORDER_EVENTS, inputMessage);

        // we should expect an output message via digital-delivery queue
        var outputMessage = digitalDeliveryQueue.poll(5, TimeUnit.SECONDS);
        Assertions.assertEquals(1, outputMessage.getPayload().orderId());
        Assertions.assertNotNull(outputMessage.getPayload().email());

        // there should not be any message from physical-delivery queue
        Assertions.assertNull(physicalDeliveryQueue.poll(1, TimeUnit.SECONDS));
    }

    @Test
    public void physicalOrder() throws InterruptedException {
        // produce a physical order via order-events
        var physicalOrder = new Order(2, 2, 2, ProductType.PHYSICAL);
        var inputMessage = MessageBuilder.withPayload(physicalOrder).build();
        this.streamBridge.send(ORDER_EVENTS, inputMessage);

        // we should expect an output message via physical-delivery queue
        var outputMessage = physicalDeliveryQueue.poll(5, TimeUnit.SECONDS);
        Assertions.assertEquals(2, outputMessage.getPayload().orderId());
        Assertions.assertNotNull(outputMessage.getPayload().street());
        Assertions.assertNotNull(outputMessage.getPayload().city());

        // there should not be any message from digital-delivery queue
        Assertions.assertNull(digitalDeliveryQueue.poll(1, TimeUnit.SECONDS));
    }

    @TestConfiguration
    static class TestConsumerConfiguration {

        @Bean
        public BlockingQueue<Message<DigitalDelivery>> digitalDeliveryQueue() {
            return new LinkedBlockingQueue<>();
        }

        @Bean
        public BlockingQueue<Message<PhysicalDelivery>> physicalDeliveryQueue() {
            return new LinkedBlockingQueue<>();
        }

        @Bean
        public Consumer<Message<DigitalDelivery>> digitalConsumer(BlockingQueue<Message<DigitalDelivery>> digitalDeliveryQueue) {
            return digitalDeliveryQueue::add;
        }

        @Bean
        public Consumer<Message<PhysicalDelivery>> physicalConsumer(BlockingQueue<Message<PhysicalDelivery>> physicalDeliveryQueue) {
            return physicalDeliveryQueue::add;
        }

    }

}
