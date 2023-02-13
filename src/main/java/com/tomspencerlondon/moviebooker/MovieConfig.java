package com.tomspencerlondon.moviebooker;

import com.tomspencerlondon.moviebooker.hexagon.application.MovieService;
import com.tomspencerlondon.moviebooker.hexagon.application.port.MovieRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovieConfig {

    @Bean
    public MovieService movieService(MovieRepository movieRepository) {
        return new MovieService(movieRepository);
    }
}
