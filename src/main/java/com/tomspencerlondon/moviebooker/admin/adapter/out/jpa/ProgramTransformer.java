package com.tomspencerlondon.moviebooker.admin.adapter.out.jpa;


import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminProgram;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieProgramDbo;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Movie;
import org.springframework.stereotype.Service;

@Service("jpaAdminProgramTransformer")
public class ProgramTransformer {

    public AdminProgram toMovieProgram(MovieProgramDbo adminProgramDbo) {
        MovieDbo movieDbo = adminProgramDbo.getMovie();
        Movie movie = new Movie(
                movieDbo.getMovieId(),
                movieDbo.getMovieName(),
                movieDbo.getMovieImage(),
                movieDbo.getReleaseDate(),
                movieDbo.getDescription());

        return new AdminProgram(
                adminProgramDbo.getScheduleId(),
                adminProgramDbo.getScheduleDate(),
                adminProgramDbo.getSeats(),
                movie,
                adminProgramDbo.getPrice());
    }
}

