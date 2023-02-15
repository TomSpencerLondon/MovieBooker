package com.tomspencerlondon.moviebooker;

import com.tomspencerlondon.moviebooker.hexagon.application.BookingService;
import com.tomspencerlondon.moviebooker.hexagon.application.MovieService;
import com.tomspencerlondon.moviebooker.hexagon.application.port.BookingRepository;
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
    public BookingService bookingService(BookingRepository bookingRepository) {
        return new BookingService(bookingRepository);
    }
}
