package com.vinsguru.playground.sec02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
abstract class AbstractTest {

    protected static final String ORDER_EVENTS = "order-events";
    protected static final String PHYSICAL_DELIVERY = "physical-delivery";
    protected static final String DIGITAL_DELIVERY = "digital-delivery";

    @Autowired
    protected StreamBridge streamBridge;

}
