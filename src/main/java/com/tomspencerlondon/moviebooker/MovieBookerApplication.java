package com.tomspencerlondon.moviebooker;

import com.tomspencerlondon.moviebooker.moviegoer.hexagon.application.MovieService;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Movie;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.MovieProgram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@SpringBootApplication
public class MovieBookerApplication implements CommandLineRunner {

    @Autowired
    MovieService movieService;

    public static void main(String[] args) {
        SpringApplication.run(MovieBookerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        bootstrapData();
    }

    private void bootstrapData() {
        String description = "Widely regarded as one of the greatest films of all time, this mob drama, based on Mario Puzo's novel of the same name, focuses on the powerful Italian-American crime family of Don Vito Corleone (Marlon Brando). When the don's youngest son, Michael (Al Pacino), reluctantly joins the Mafia, he becomes involved in the inevitable cycle of violence and betrayal. Although Michael tries to maintain a normal relationship with his wife, Kay (Diane Keaton), he is drawn deeper into the family business.";
        Movie movie = new Movie(1L,
                "Godfather",
                "/img/godfather.jpg",
                LocalDate.of(1975, 8, 24)
                , description);


        Movie savedMovie = movieService.saveMovie(movie);
        MovieProgram movieProgram = new MovieProgram(1L,
                LocalDateTime.of(2023, 2, 14, 13, 0),
                20,
                savedMovie, 0, new BigDecimal("5.00"));

        movieService.saveMovieProgram(movieProgram);
    }
}
