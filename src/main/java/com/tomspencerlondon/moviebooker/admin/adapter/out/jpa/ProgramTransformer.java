package com.tomspencerlondon.moviebooker.admin.adapter.out.jpa;


import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminMovie;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminProgram;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieProgramDbo;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Movie;
import org.springframework.stereotype.Service;

@Service("jpaAdminProgramTransformer")
public class ProgramTransformer {

    private final AdminMovieTransformer adminMovieTransformer;

    public ProgramTransformer(AdminMovieTransformer adminMovieTransformer) {
        this.adminMovieTransformer = adminMovieTransformer;
    }

    public AdminProgram toMovieProgram(MovieProgramDbo adminProgramDbo) {
        MovieDbo movieDbo = adminProgramDbo.getMovie();
        AdminMovie movie = new AdminMovie(
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

    public MovieProgramDbo fromMovieProgram(AdminProgram adminProgram) {
        MovieProgramDbo movieProgramDbo = new MovieProgramDbo();
        MovieDbo movieDbo = adminMovieTransformer.toMovieDbo(adminProgram.movie());

        movieProgramDbo.setScheduleId(adminProgram.getScheduleId());
        movieProgramDbo.setMovie(movieDbo);
        movieProgramDbo.setScheduleDate(adminProgram.scheduleDate());
        movieProgramDbo.setSeats(adminProgram.totalSeats());
        movieProgramDbo.setPrice(adminProgram.price());

        return movieProgramDbo;
    }
}

