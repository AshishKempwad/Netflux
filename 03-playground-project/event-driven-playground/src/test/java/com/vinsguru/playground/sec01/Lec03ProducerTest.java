package com.vinsguru.playground.sec01;

import com.vinsguru.playground.sec19.SectionRunner;
import com.vinsguru.playground.sec19.dto.Order;
import com.vinsguru.playground.sec19.dto.ProductType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.KafkaHeaders;

@SpringBootTest(
        classes = SectionRunner.Producer.class,
        properties = {
                "section=sec19",
                "config=04-producer"
        }
)
class Lec03ProducerTest extends AbstractTest {

    @Test
    public void orderProducer(){
        var message1 = this.receive(ORDER_EVENTS, Order.class);
        var message2 = this.receive(ORDER_EVENTS, Order.class);

        // we can not use receivedKey. We use test binder and we test how the message is produced
        Assertions.assertEquals(1, message1.getHeaders().get(KafkaHeaders.KEY, Integer.class));
        Assertions.assertEquals(1, message1.getPayload().id());
        Assertions.assertEquals(ProductType.DIGITAL, message1.getPayload().productType());

        Assertions.assertEquals(2, message2.getHeaders().get(KafkaHeaders.KEY, Integer.class));
        Assertions.assertEquals(2, message2.getPayload().id());
        Assertions.assertEquals(ProductType.PHYSICAL, message2.getPayload().productType());
    }

}
