package com.tomspencerlondon.moviebooker.moviegoer.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.BookingDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.BookingJpaRepository;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.application.MovieService;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Booking;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Movie;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.MovieGoer;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.MovieProgram;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;


@Tag("integration")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers(disabledWithoutDocker = true)
public class BookingRepositoryTest extends TestContainerConfiguration {
    @Autowired
    private BookingJpaRepository bookingJpaRepository;

    @MockBean
    MovieService movieService;

    @Test
    void stores_and_retrieves_Questions() {
        Movie movie = new Movie(1L, "Godfather", "image", LocalDate.MIN, "description");
        MovieProgram movieProgram = new MovieProgram(
                null,
                LocalDateTime.now(),
                11,
                movie,
                0,
                new BigDecimal(5)
        );
        Booking booking = new Booking(1L,
                movieProgram,
                5,
                new BigDecimal(5)
        );

        MovieGoer movieGoer = new MovieGoer("moviegoer", "password", 5, true, true);
        BookingDbo bookingDbo = new BookingDboBuilder().from(booking).with(movieGoer).build();
        BookingDbo savedBookingDbo = bookingJpaRepository.save(bookingDbo);

        bookingJpaRepository.delete(savedBookingDbo);

        Optional<BookingDbo> searchedForBooking = bookingJpaRepository.findById(savedBookingDbo.getBookingId());

        assertThat(searchedForBooking)
                .isEmpty();

    }
}