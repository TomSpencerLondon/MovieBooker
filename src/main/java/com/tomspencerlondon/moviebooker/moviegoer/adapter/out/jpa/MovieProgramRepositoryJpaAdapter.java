package com.tomspencerlondon.moviebooker.moviegoer.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.moviegoer.hexagon.application.port.MovieProgramRepository;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.MovieProgram;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MovieProgramRepositoryJpaAdapter implements MovieProgramRepository {

    private final MovieProgramJpaRepository movieProgramJpaRepository;
    private final MovieProgramTransformer movieProgramTransformer;

    private final MovieTransformer movieTransformer;

    private final BookingJpaRepository bookingJpaRepository;

    public MovieProgramRepositoryJpaAdapter(MovieProgramJpaRepository movieProgramJpaRepository,
                                            MovieProgramTransformer movieProgramTransformer,
                                            MovieTransformer movieTransformer, BookingJpaRepository bookingJpaRepository) {
        this.movieProgramJpaRepository = movieProgramJpaRepository;
        this.movieProgramTransformer = movieProgramTransformer;
        this.movieTransformer = movieTransformer;
        this.bookingJpaRepository = bookingJpaRepository;
    }

    @Override
    public MovieProgram save(MovieProgram movieProgram) {
        MovieProgramDbo movieProgramDbo = movieProgramTransformer.toMovieProgramDbo(movieProgram);
        MovieProgramDbo saved = movieProgramJpaRepository.save(movieProgramDbo);
        BookingDboCollection bookingDboCollection = new BookingDboCollection(bookingJpaRepository.findBookingsByProgramId(movieProgramDbo.getScheduleId()));
        return new MovieProgram(saved.getScheduleId(), saved.getScheduleDate(), saved.getSeats(),
                movieTransformer.toMovie(saved.getMovie()), bookingDboCollection.totalSeatsBooked(), saved.getPrice());
    }

    @Override
    public List<MovieProgram> findAll() {
        return null;
    }

    @Override
    public List<MovieProgram> findByMovieId(Long movieId) {
        return movieProgramJpaRepository.findMovieProgramDbosBy(movieId)
                .stream().map(movieProgramDbo ->
                        movieProgramTransformer.toMovieProgram(movieProgramDbo,
                                new BookingDboCollection(bookingJpaRepository.findBookingsByProgramId(movieProgramDbo.getScheduleId()))
                                        .totalSeatsBooked()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MovieProgram> findById(Long scheduleId) {
        return movieProgramJpaRepository.findById(scheduleId)
                .map(movieProgramDbo -> movieProgramTransformer.toMovieProgram(movieProgramDbo,
                        new BookingDboCollection(bookingJpaRepository.findBookingsByProgramId(movieProgramDbo.getScheduleId()))
                                .totalSeatsBooked()));
    }


}
