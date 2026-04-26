package com.akempwad.recommendation.service;

import com.akempwad.netflux.events.CustomerGenreUpdatedEvent;
import com.akempwad.recommendation.dto.RecommendationEvents;
import com.akempwad.recommendation.mapper.RecommendationMapper;
import com.akempwad.recommendation.repository.CustomerGenreRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerGenreRepository customerGenreRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public CustomerService(CustomerGenreRepository customerGenreRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.customerGenreRepository = customerGenreRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void updateGenre(CustomerGenreUpdatedEvent genreUpdatedEvent){
        var entity = RecommendationMapper.toCustomerGenre(genreUpdatedEvent);
        this.customerGenreRepository.save(entity);
        this.applicationEventPublisher.publishEvent(new RecommendationEvents.PersonalizedEvent(genreUpdatedEvent.customerId()));
    }

}
