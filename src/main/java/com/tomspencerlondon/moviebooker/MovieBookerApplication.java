package com.tomspencerlondon.moviebooker;

import com.tomspencerlondon.moviebooker.adapter.out.jpa.MovieDbo;
import com.tomspencerlondon.moviebooker.adapter.out.jpa.MovieJpaRepository;
import com.tomspencerlondon.moviebooker.adapter.out.jpa.MovieProgramDbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class MovieBookerApplication implements CommandLineRunner {
    @Autowired
    MovieJpaRepository movieJpaRepository;

    public static void main(String[] args) {
        SpringApplication.run(MovieBookerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        MovieDbo movieDbo = new MovieDbo();
        movieDbo.setMovieImage("/img/godfather.jpg");
        movieDbo.setDescription("Widely regarded as one of the greatest films of all time, this mob drama, based on Mario Puzo's novel of the same name, focuses on the powerful Italian-American crime family of Don Vito Corleone (Marlon Brando). When the don's youngest son, Michael (Al Pacino), reluctantly joins the Mafia, he becomes involved in the inevitable cycle of violence and betrayal. Although Michael tries to maintain a normal relationship with his wife, Kay (Diane Keaton), he is drawn deeper into the family business.");
        movieDbo.setReleaseDate(LocalDate.of(1975, 8, 24));
        movieDbo.setMovieName("Godfather");
        MovieProgramDbo program = new MovieProgramDbo();
        program.setScheduleDate(LocalDateTime.of(2023, 2, 14, 13, 0));
        movieDbo.setMoviePrograms(List.of(
                program
        ));

        movieJpaRepository.saveAndFlush(movieDbo);

    }
}
