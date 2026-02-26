package com.vinsguru.recommendation.service;

import com.vinsguru.netflux.events.MovieAddedEvent;
import com.vinsguru.recommendation.dto.RecommendationEvents;
import com.vinsguru.recommendation.mapper.RecommendationMapper;
import com.vinsguru.recommendation.repository.MovieRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ApplicationEventPublisher eventPublisher;

    public MovieService(MovieRepository movieRepository, ApplicationEventPublisher eventPublisher) {
        this.movieRepository = movieRepository;
        this.eventPublisher = eventPublisher;
    }

    public void addMovie(MovieAddedEvent movieAddedEvent){
        var entity = RecommendationMapper.toMovie(movieAddedEvent);
        this.movieRepository.save(entity);
        this.eventPublisher.publishEvent(new RecommendationEvents.NewMovieEvent(movieAddedEvent.movieId()));
    }

}
