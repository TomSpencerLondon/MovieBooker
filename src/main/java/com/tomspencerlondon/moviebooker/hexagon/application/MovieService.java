package com.tomspencerlondon.moviebooker.hexagon.application;

import com.tomspencerlondon.moviebooker.hexagon.application.port.MovieProgramRepository;
import com.tomspencerlondon.moviebooker.hexagon.application.port.MovieRepository;
import com.tomspencerlondon.moviebooker.hexagon.domain.Movie;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieProgram;

import java.util.List;

public class MovieService {

    private MovieRepository movieRepository;
    private MovieProgramRepository movieProgramRepository;

    public MovieService(MovieRepository movieRepository, MovieProgramRepository movieProgramRepository) {
        this.movieRepository = movieRepository;
        this.movieProgramRepository = movieProgramRepository;
    }

    public Movie findById(Long movieId) {
        return movieRepository.findById(movieId).orElseThrow(UnsupportedOperationException::new);
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public List<MovieProgram> programsForFilm(Long filmId) {
        Movie movie = movieRepository.findById(filmId)
                .orElseThrow(UnsupportedOperationException::new);

        Long movieId = movie.getId();

        return movieProgramRepository.findByMovieId(movieId);
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public MovieProgram saveMovieProgram(MovieProgram movieProgram) {
        return movieProgramRepository.save(movieProgram);
    }

    public MovieProgram findMovieProgramBy(Long scheduleId) {
        return movieProgramRepository.findById(scheduleId).orElseThrow(IllegalArgumentException::new);
    }

    public boolean areSeatsAvailable(Long scheduleId, int additionalSeats) {
        MovieProgram movieProgram = findMovieProgramBy(scheduleId);

        return movieProgram.seatsAvailableFor(additionalSeats);
    }
}
