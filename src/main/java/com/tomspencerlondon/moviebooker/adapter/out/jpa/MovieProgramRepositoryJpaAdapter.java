package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.hexagon.application.port.MovieProgramRepository;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieProgram;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MovieProgramRepositoryJpaAdapter implements MovieProgramRepository {

    private final MovieProgramJpaRepository movieProgramJpaRepository;
    private final MovieProgramTransformer movieProgramTransformer;

    private final MovieTransformer movieTransformer;

    public MovieProgramRepositoryJpaAdapter(MovieProgramJpaRepository movieProgramJpaRepository, MovieProgramTransformer movieProgramTransformer, MovieTransformer movieTransformer) {
        this.movieProgramJpaRepository = movieProgramJpaRepository;
        this.movieProgramTransformer = movieProgramTransformer;
        this.movieTransformer = movieTransformer;
    }

    @Override
    public MovieProgram save(MovieProgram movieProgram) {
        MovieProgramDbo movieProgramDbo = new MovieProgramDbo();
        movieProgramDbo.setScheduleId(movieProgram.getScheduleId());
        movieProgramDbo.setMovie(movieTransformer.toMovieDbo(movieProgram.movie()));
        movieProgramDbo.setScheduleDate(movieProgram.scheduleDate());
        movieProgramDbo.setSeats(movieProgram.seats());

        MovieProgramDbo saved = movieProgramJpaRepository.save(movieProgramDbo);
        return new MovieProgram(saved.getScheduleId(), saved.getScheduleDate(), saved.getSeats(),
                movieTransformer.toMovie(saved.getMovie()));
    }

    @Override
    public List<MovieProgram> findAll() {
        return null;
    }

    @Override
    public List<MovieProgram> findByMovieId(Long movieId) {
        return movieProgramJpaRepository.findMovieProgramDbosBy(movieId)
                .stream().map(movieProgramTransformer::toMovieProgram).collect(Collectors.toList());
    }

    @Override
    public Optional<MovieProgram> findById(Long scheduleId) {
        return movieProgramJpaRepository.findById(scheduleId)
                .map(movieProgramTransformer::toMovieProgram);
    }


}
