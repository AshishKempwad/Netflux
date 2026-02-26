package com.vinsguru.recommendation.controller;

import com.vinsguru.recommendation.dto.MovieRecommendations;
import com.vinsguru.recommendation.service.RecommendationService;
import com.vinsguru.recommendation.service.RecommendationStreamService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final RecommendationStreamService recommendationStreamService;

    public RecommendationController(RecommendationService recommendationService, RecommendationStreamService recommendationStreamService) {
        this.recommendationService = recommendationService;
        this.recommendationStreamService = recommendationStreamService;
    }

    @GetMapping("/{customerId}")
    public List<MovieRecommendations> getRecommendations(@PathVariable Integer customerId){
        return List.of(
            MovieRecommendations.newlyAdded(this.recommendationService.findNewlyAdded()),
            MovieRecommendations.personalized(customerId, this.recommendationService.findPersonalized(customerId))
        );
    }

    @GetMapping(value = "/{customerId}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MovieRecommendations> getRecommendationStream(@PathVariable Integer customerId){
        return this.recommendationStreamService.streamRecommendations(customerId);
    }

}
