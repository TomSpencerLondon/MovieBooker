package com.tomspencerlondon.moviebooker.hexagon.application.port;

import com.tomspencerlondon.moviebooker.hexagon.domain.Movie;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieProgram;

import java.util.List;

public interface MovieProgramRepository {
    MovieProgram save(MovieProgram movieProgram);
    List<MovieProgram> findAll();

    List<MovieProgram> findByMovieId(Long movieId);
}
