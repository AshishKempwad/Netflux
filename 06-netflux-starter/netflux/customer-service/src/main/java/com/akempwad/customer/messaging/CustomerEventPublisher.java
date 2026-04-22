package com.akempwad.customer.messaging;

import com.akempwad.netflux.events.CustomerGenreUpdatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CustomerEventPublisher {

    @EventListener
    public void onGenreUpdatedEvent(CustomerGenreUpdatedEvent genreUpdatedEvent) {

    }

}
