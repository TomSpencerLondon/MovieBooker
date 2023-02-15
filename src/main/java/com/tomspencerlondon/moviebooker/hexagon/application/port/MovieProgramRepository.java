package com.tomspencerlondon.moviebooker.hexagon.application.port;

import com.tomspencerlondon.moviebooker.hexagon.domain.MovieProgram;

import java.util.List;
import java.util.Optional;

public interface MovieProgramRepository {
    MovieProgram save(MovieProgram movieProgram);
    List<MovieProgram> findAll();

    List<MovieProgram> findByMovieId(Long movieId);

    Optional<MovieProgram> findById(Long scheduleId);
}
