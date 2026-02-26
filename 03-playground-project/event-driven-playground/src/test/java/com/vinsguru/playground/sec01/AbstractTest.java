package com.vinsguru.playground.sec01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.test.EnableTestBinder;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import tools.jackson.databind.json.JsonMapper;

@EnableTestBinder
abstract class AbstractTest {

    protected static final String ORDER_EVENTS = "order-events";
    protected static final String PHYSICAL_DELIVERY = "physical-delivery";
    protected static final String DIGITAL_DELIVERY = "digital-delivery";

    @Autowired
    protected InputDestination inputDestination;

    @Autowired
    protected OutputDestination outputDestination;

    protected <T> Message<T> receive(String destination, Class<T> type){
        var message = this.outputDestination.receive(1000, destination);
        var payload = JsonMapper.shared().readValue(message.getPayload(), type);
        return MessageBuilder.createMessage(payload, message.getHeaders());
    }
}
