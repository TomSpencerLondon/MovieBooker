package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.hexagon.application.port.MovieRepository;
import com.tomspencerlondon.moviebooker.hexagon.domain.Movie;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MovieRepositoryJpaAdapter implements MovieRepository {
    private final MovieJpaRepository movieJpaRepository;
    private final MovieTransformer movieTransformer;


    public MovieRepositoryJpaAdapter(MovieJpaRepository movieJpaRepository, MovieTransformer movieTransformer) {
        this.movieJpaRepository = movieJpaRepository;
        this.movieTransformer = movieTransformer;
    }

    @Override
    public Movie save(Movie movie) {
        MovieDbo movieDbo = movieTransformer.toMovieDbo(movie);
        MovieDbo savedMovieDbo = movieJpaRepository.save(movieDbo);

        return movieTransformer.toMovie(savedMovieDbo);
    }

    @Override
    public List<Movie> findAll() {
        return movieJpaRepository.findAll()
                .stream()
                .map(movieTransformer::toMovie)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Movie> findById(Long movieId) {
        return movieJpaRepository.findById(movieId)
                .map(movieTransformer::toMovie);
    }
}
