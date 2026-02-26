package com.vinsguru.playground.sec13.processor;

import com.vinsguru.playground.sec13.dto.DigitalDelivery;
import com.vinsguru.playground.sec13.dto.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Function;

@Configuration
public class ProcessorConfig {

    private static final Logger log = LoggerFactory.getLogger(ProcessorConfig.class);
    private static final String SPRING_CLOUD_STREAM_DESTINATION = "spring.cloud.stream.sendto.destination";
    private static final String DIGITAL_OUT = "digital-delivery-out";
    private static final String PHYSICAL_OUT = "physical-delivery-out";

    @Bean // spring uses 1 thread per partition to execute this concurrently
    public Function<Order, Message<?>> deliveryProcessor(DeliveryService deliveryService) {
        return order -> this.toMessage(deliveryService.toDelivery(order));
    }

    private Message<?> toMessage(Object payload) {
        log.info("dispatching: {}", payload);
        var destination = (payload instanceof DigitalDelivery) ? DIGITAL_OUT : PHYSICAL_OUT;
        return MessageBuilder.withPayload(payload)
                             .setHeader(SPRING_CLOUD_STREAM_DESTINATION, destination)
                             .build();
    }

}
