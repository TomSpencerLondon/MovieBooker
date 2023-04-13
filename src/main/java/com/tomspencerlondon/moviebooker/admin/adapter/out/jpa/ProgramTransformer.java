package com.tomspencerlondon.moviebooker.admin.adapter.out.jpa;


import com.tomspencerlondon.moviebooker.admin.hexagon.domain.Program;
import com.tomspencerlondon.moviebooker.moviegoer.adapter.out.jpa.MovieDbo;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Movie;
import org.springframework.stereotype.Service;

@Service("jpaAdminProgramTransformer")
public class ProgramTransformer {

    public Program toMovieProgram(AdminProgramDbo adminProgramDbo) {
        MovieDbo movieDbo = adminProgramDbo.getMovie();
        Movie movie = new Movie(
                movieDbo.getMovieId(),
                movieDbo.getMovieName(),
                movieDbo.getMovieImage(),
                movieDbo.getReleaseDate(),
                movieDbo.getDescription());

        return new Program(
                adminProgramDbo.getScheduleId(),
                adminProgramDbo.getScheduleDate(),
                adminProgramDbo.getSeats(),
                movie,
                adminProgramDbo.getPrice());
    }
}

