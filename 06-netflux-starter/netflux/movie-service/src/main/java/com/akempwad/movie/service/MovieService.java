package com.akempwad.movie.service;


import com.akempwad.movie.dto.MovieDetails;
import com.akempwad.movie.exception.MovieNotFoundException;
import com.akempwad.movie.mapper.MovieMapper;
import com.akempwad.movie.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public MovieService(MovieRepository movieRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.movieRepository = movieRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public MovieDetails getMovie(Integer movieId) {
        return this.movieRepository.findById(movieId)
                .map(movie-> MovieMapper.toMovieDetails(movie))
                .orElseThrow(()->new MovieNotFoundException(movieId));
    }


    @Transactional
    public MovieDetails saveMovie(MovieDetails movieDetails) {
        var movie = this.movieRepository.save(MovieMapper.toMovie(movieDetails));
        this.applicationEventPublisher.publishEvent(MovieMapper.toMovieAddedEvent(movie));
        return MovieMapper.toMovieDetails(movie);
    }

}
