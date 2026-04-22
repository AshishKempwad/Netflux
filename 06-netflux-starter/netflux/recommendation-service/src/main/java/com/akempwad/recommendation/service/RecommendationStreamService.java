package com.akempwad.recommendation.service;

import com.akempwad.recommendation.dto.MovieRecommendations;
import com.akempwad.recommendation.dto.RecommendationEvents;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class RecommendationStreamService { // RecommendationNotifier

    public Flux<MovieRecommendations> streamRecommendations(Integer customerId){
        return Flux.empty();
    }

    @Async
    @EventListener // global
    public void onMovieAdded(RecommendationEvents.NewMovieEvent event){

    }

    @Async
    @EventListener
    public void onPersonalized(RecommendationEvents.PersonalizedEvent event){

    }

}

