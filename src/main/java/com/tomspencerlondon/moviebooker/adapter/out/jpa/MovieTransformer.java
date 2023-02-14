package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.hexagon.domain.Movie;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieProgram;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service("jpaQuestionTransformer")
public class MovieTransformer {

    public Movie toMovie(MovieDbo movieDbo) {
        List<MovieProgram> moviePrograms = movieDbo.getMoviePrograms()
                .stream()
                .map(m -> new MovieProgram(m.getScheduleId(), m.getScheduleDate(), m.getSeats()))
                .toList();

        return new Movie(
                movieDbo.getId(),
                movieDbo.getMovieName(),
                movieDbo.getMovieImage(),
                movieDbo.getReleaseDate(),
                movieDbo.getDescription(),
                moviePrograms
        );
    }
}
