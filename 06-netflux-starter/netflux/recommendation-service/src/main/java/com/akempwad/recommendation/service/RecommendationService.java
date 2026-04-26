package com.akempwad.recommendation.service;

import com.akempwad.recommendation.dto.MovieSummary;
import com.akempwad.recommendation.mapper.RecommendationMapper;
import com.akempwad.recommendation.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RecommendationService {

    private final MovieRepository movieRepository;

    public RecommendationService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<MovieSummary> findNewlyAdded() {

        return this.movieRepository.findTop10ByOrderByCreatedAtDesc()
                .stream()
                .map(movie ->RecommendationMapper.toMovieSummary(movie))
                .toList();
    }

    public List<MovieSummary> findPersonalized(Integer customerId) {

        return this.movieRepository.findPersonalized(customerId)
                .stream()
                .map(movie -> RecommendationMapper.toMovieSummary(movie))
                .toList();
    }

    public MovieSummary findMovie(Integer movieId) {

        return this.movieRepository.findById(movieId)
                .map(movie -> RecommendationMapper.toMovieSummary(movie))
                .orElseThrow();
    }

}
