package com.tomspencerlondon.moviebooker;

import com.tomspencerlondon.moviebooker.hexagon.application.BookingService;
import com.tomspencerlondon.moviebooker.hexagon.application.CustomUserDetailsService;
import com.tomspencerlondon.moviebooker.hexagon.application.MovieGoerService;
import com.tomspencerlondon.moviebooker.hexagon.application.MovieService;
import com.tomspencerlondon.moviebooker.hexagon.application.port.BookingRepository;
import com.tomspencerlondon.moviebooker.hexagon.application.port.MovieGoerRepository;
import com.tomspencerlondon.moviebooker.hexagon.application.port.MovieProgramRepository;
import com.tomspencerlondon.moviebooker.hexagon.application.port.MovieRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovieConfig {

    @Bean
    public MovieService movieService(MovieRepository movieRepository, MovieProgramRepository movieProgramRepository) {
        return new MovieService(movieRepository, movieProgramRepository);
    }

    @Bean
    public BookingService bookingService(BookingRepository bookingRepository, MovieProgramRepository movieProgramRepository) {
        return new BookingService(bookingRepository, movieProgramRepository);
    }

    @Bean
    public MovieGoerService movieGoerService(MovieGoerRepository movieGoerRepository) {
        return new MovieGoerService(movieGoerRepository);
    }

    @Bean
    CustomUserDetailsService customUserDetailsService(MovieGoerRepository movieGoerRepository) {
        return new CustomUserDetailsService(movieGoerRepository);
    }
}
