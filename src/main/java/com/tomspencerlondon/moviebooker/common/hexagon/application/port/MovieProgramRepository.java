package com.tomspencerlondon.moviebooker.common.hexagon.application.port;

import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.MovieProgram;

import java.util.List;
import java.util.Optional;

public interface MovieProgramRepository {
    List<MovieProgram> findByMovieId(Long movieId);

    Optional<MovieProgram> findById(Long scheduleId);

    List<MovieProgram> current();

    List<MovieProgram> future();
}
