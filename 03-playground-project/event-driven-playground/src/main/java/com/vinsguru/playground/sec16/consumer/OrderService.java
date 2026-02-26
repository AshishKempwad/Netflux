package com.vinsguru.playground.sec16.consumer;

import com.vinsguru.playground.sec16.exceptions.ServiceUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    public void saveOrder(Integer orderId){
        if(orderId > 5){
            this.simulateTransientFailure();
        }
        log.info("Order {} saved successfully", orderId);
    }

    private void simulateTransientFailure() {
        int random = ThreadLocalRandom.current().nextInt(1, 11);
        log.info("random: {}", random);

        if (random < 8) {
            log.warn("service unavailable");
            throw new ServiceUnavailableException();
        }
    }

}
