package com.tomspencerlondon.moviebooker.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.hexagon.application.port.MovieProgramRepository;
import com.tomspencerlondon.moviebooker.hexagon.domain.Booking;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieProgram;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MovieProgramRepositoryJpaAdapter implements MovieProgramRepository {

    private final MovieProgramJpaRepository movieProgramJpaRepository;
    private final MovieProgramTransformer movieProgramTransformer;
    private final BookingTransformer bookingTransformer;

    private final MovieTransformer movieTransformer;

    public MovieProgramRepositoryJpaAdapter(MovieProgramJpaRepository movieProgramJpaRepository,
                                            MovieProgramTransformer movieProgramTransformer,
                                            BookingTransformer bookingTransformer, MovieTransformer movieTransformer) {
        this.movieProgramJpaRepository = movieProgramJpaRepository;
        this.movieProgramTransformer = movieProgramTransformer;
        this.bookingTransformer = bookingTransformer;
        this.movieTransformer = movieTransformer;
    }

    @Override
    public MovieProgram save(MovieProgram movieProgram) {
        MovieProgramDbo movieProgramDbo = new MovieProgramDbo();
        movieProgramDbo.setScheduleId(movieProgram.getScheduleId());
        movieProgramDbo.setMovie(movieTransformer.toMovieDbo(movieProgram.movie()));
        movieProgramDbo.setScheduleDate(movieProgram.scheduleDate());
        movieProgramDbo.setSeats(movieProgram.totalSeats());
        movieProgramDbo.setPrice(movieProgram.price());

        MovieProgramDbo saved = movieProgramJpaRepository.save(movieProgramDbo);

        List<Booking> bookings = movieProgramDbo.getBookings()
                .stream()
                .map(bookingTransformer::toBooking).toList();

        return new MovieProgram(saved.getScheduleId(), saved.getScheduleDate(), saved.getSeats(),
                movieTransformer.toMovie(saved.getMovie()), bookings, saved.getPrice());
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
