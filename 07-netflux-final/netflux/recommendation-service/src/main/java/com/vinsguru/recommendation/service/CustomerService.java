package com.vinsguru.recommendation.service;

import com.vinsguru.netflux.events.CustomerGenreUpdatedEvent;
import com.vinsguru.recommendation.dto.RecommendationEvents;
import com.vinsguru.recommendation.mapper.RecommendationMapper;
import com.vinsguru.recommendation.repository.CustomerGenreRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerGenreRepository customerGenreRepository;
    private final ApplicationEventPublisher eventPublisher;

    public CustomerService(CustomerGenreRepository customerGenreRepository, ApplicationEventPublisher eventPublisher) {
        this.customerGenreRepository = customerGenreRepository;
        this.eventPublisher = eventPublisher;
    }

    public void updateGenre(CustomerGenreUpdatedEvent genreUpdatedEvent){
        var entity = RecommendationMapper.toCustomerGenre(genreUpdatedEvent);
        this.customerGenreRepository.save(entity);
        this.eventPublisher.publishEvent(new RecommendationEvents.PersonalizedEvent(genreUpdatedEvent.customerId()));
    }

}
