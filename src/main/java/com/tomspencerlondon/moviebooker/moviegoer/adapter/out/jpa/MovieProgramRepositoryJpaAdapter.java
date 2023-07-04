package com.tomspencerlondon.moviebooker.moviegoer.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.common.hexagon.application.port.ScreenRepository;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.BookingDboCollection;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.BookingJpaRepository;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieProgramDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieProgramJpaRepository;
import com.tomspencerlondon.moviebooker.common.hexagon.application.port.MovieProgramRepository;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.MovieProgram;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MovieProgramRepositoryJpaAdapter implements MovieProgramRepository {

    private final MovieProgramJpaRepository movieProgramJpaRepository;
    private final MovieProgramTransformer movieProgramTransformer;

    private final BookingJpaRepository bookingJpaRepository;
    private final ScreenRepository screenRepository;

    public MovieProgramRepositoryJpaAdapter(MovieProgramJpaRepository movieProgramJpaRepository,
                                            MovieProgramTransformer movieProgramTransformer,
        BookingJpaRepository bookingJpaRepository,
        ScreenRepository screenRepository) {
        this.movieProgramJpaRepository = movieProgramJpaRepository;
        this.movieProgramTransformer = movieProgramTransformer;
        this.bookingJpaRepository = bookingJpaRepository;
        this.screenRepository = screenRepository;
    }

    @Override
    public List<MovieProgram> findByMovieId(Long movieId) {

        return movieProgramJpaRepository.findMovieProgramDbosBy(movieId)
                .stream().map(movieProgramDbo ->
                        getMovieProgram(movieProgramDbo))
                .collect(Collectors.toList());
    }

    private MovieProgram getMovieProgram(MovieProgramDbo movieProgramDbo) {
        int totalSeats = screenRepository.findById(movieProgramDbo.getScreenId()).numberOfSeats();
        return movieProgramTransformer.toMovieProgram(movieProgramDbo,
                new BookingDboCollection(bookingJpaRepository.findBookingsByProgramId(movieProgramDbo.getScheduleId()))
                        .totalSeatsBooked(), totalSeats);
    }

    @Override
    public Optional<MovieProgram> findById(Long scheduleId) {
        return movieProgramJpaRepository.findById(scheduleId)
                .map(movieProgramDbo -> getMovieProgram(movieProgramDbo));
    }



}
