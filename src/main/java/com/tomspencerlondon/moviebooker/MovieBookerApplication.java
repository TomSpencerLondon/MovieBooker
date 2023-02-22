package com.tomspencerlondon.moviebooker;

import com.tomspencerlondon.moviebooker.hexagon.application.MovieService;
import com.tomspencerlondon.moviebooker.hexagon.domain.Movie;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieProgram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

@SpringBootApplication
public class MovieBookerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieBookerApplication.class, args);
    }

}
