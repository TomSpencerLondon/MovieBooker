package com.tomspencerlondon.moviebooker.admin.adapter.out.jpa;


import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminMovie;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminProgram;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.Screen;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieProgramDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.ScreenDbo;
import org.springframework.stereotype.Service;

@Service("jpaAdminProgramTransformer")
public class ProgramTransformer {

    private final AdminMovieTransformer adminMovieTransformer;

    public ProgramTransformer(AdminMovieTransformer adminMovieTransformer) {
        this.adminMovieTransformer = adminMovieTransformer;
    }

    public AdminProgram toMovieProgram(MovieProgramDbo adminProgramDbo, ScreenDbo screenDbo) {
        MovieDbo movieDbo = adminProgramDbo.getMovie();
        AdminMovie movie = new AdminMovie(
                movieDbo.getMovieId(),
                movieDbo.getMovieName(),
                movieDbo.getMovieImage(),
                movieDbo.getReleaseDate(),
                movieDbo.getDescription());

        Screen screen = new Screen(screenDbo.getScreenId(), screenDbo.getNumberOfSeats());

        return new AdminProgram(
                adminProgramDbo.getScheduleId(),
                adminProgramDbo.getScheduleDate(),
                screen,
                movie,
                adminProgramDbo.getPrice());
    }

    public MovieProgramDbo fromMovieProgram(AdminProgram adminProgram) {
        MovieProgramDbo movieProgramDbo = new MovieProgramDbo();
        MovieDbo movieDbo = adminMovieTransformer.toMovieDbo(adminProgram.movie());

        movieProgramDbo.setScheduleId(adminProgram.getScheduleId());
        movieProgramDbo.setMovie(movieDbo);
        movieProgramDbo.setScheduleDate(adminProgram.scheduleDate());
        movieProgramDbo.setScreenId(adminProgram.getScreen().getId());
        movieProgramDbo.setPrice(adminProgram.price());

        return movieProgramDbo;
    }
}

