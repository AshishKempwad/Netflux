package com.vinsguru.playground.sec01;

import com.vinsguru.playground.sec19.SectionRunner;
import com.vinsguru.playground.sec19.dto.DigitalDelivery;
import com.vinsguru.playground.sec19.dto.PhysicalDelivery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.messaging.support.MessageBuilder;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(
        classes = SectionRunner.PhysicalDeliveryConsumer.class,
        properties = {
                "section=sec19",
                "config=02-physical-consumer"
        }
)
class Lec02PhysicalConsumerTest extends AbstractTest {

    @Test
    public void physicalConsumer(CapturedOutput output) {
        var physicalDelivery = new PhysicalDelivery(1, "123 non main street", "atlanta");
        var message = MessageBuilder.withPayload(physicalDelivery).build();
        this.inputDestination.send(message, PHYSICAL_DELIVERY);
        Assertions.assertTrue(output.getOut().contains("received: " + physicalDelivery));
    }

}
