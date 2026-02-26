package com.vinsguru.playground.sec01;

import com.vinsguru.playground.sec19.SectionRunner;
import com.vinsguru.playground.sec19.dto.DigitalDelivery;
import com.vinsguru.playground.sec19.dto.Order;
import com.vinsguru.playground.sec19.dto.PhysicalDelivery;
import com.vinsguru.playground.sec19.dto.ProductType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootTest(
        classes = SectionRunner.Processor.class,
        properties = {
                "section=sec19",
                "config=03-processor"
        }
)
class Lec04ProcessorTest extends AbstractTest {

    @Test
    public void digitalOrder(){
        // produce a digital order via order-events
        var digitalOrder = new Order(1, 1, 1, ProductType.DIGITAL);
        var inputMessage = MessageBuilder.withPayload(digitalOrder).build();
        this.inputDestination.send(inputMessage, ORDER_EVENTS);

        // we should expect an output message via digital-delivery topic
        var outputMessage = this.receive(DIGITAL_DELIVERY, DigitalDelivery.class);
        Assertions.assertEquals(1, outputMessage.getPayload().orderId());
        Assertions.assertNotNull(outputMessage.getPayload().email());

        // there should not be any message from physical-delivery topic
        Assertions.assertNull(this.outputDestination.receive(1000, PHYSICAL_DELIVERY));
    }

    @Test
    public void physicalOrder(){
        // produce a physical order via order-events
        var physicalOrder = new Order(2, 2, 2, ProductType.PHYSICAL);
        var inputMessage = MessageBuilder.withPayload(physicalOrder).build();
        this.inputDestination.send(inputMessage, ORDER_EVENTS);

        // we should expect an output message via physical-delivery topic
        var outputMessage = this.receive(PHYSICAL_DELIVERY, PhysicalDelivery.class);
        Assertions.assertEquals(2, outputMessage.getPayload().orderId());
        Assertions.assertNotNull(outputMessage.getPayload().street());
        Assertions.assertNotNull(outputMessage.getPayload().city());

        // there should not be any message from digital-delivery topic
        Assertions.assertNull(this.outputDestination.receive(1000, DIGITAL_DELIVERY));
    }

}
