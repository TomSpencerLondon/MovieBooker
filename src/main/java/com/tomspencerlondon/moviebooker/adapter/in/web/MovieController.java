package com.tomspencerlondon.moviebooker.adapter.in.web;

import com.tomspencerlondon.moviebooker.hexagon.application.BookingService;
import com.tomspencerlondon.moviebooker.hexagon.application.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MovieController {

    private final MovieService movieService;
    private final BookingService bookingService;

    public MovieController(MovieService movieService, BookingService bookingService) {
        this.movieService = movieService;
        this.bookingService = bookingService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("movies", movieService.findAll());
        return "start";
    }

    @GetMapping("/movie")
    public String movie(Model model, @RequestParam(value = "filmId", defaultValue = "") String filmId) {
        Long filmIdLong = Long.valueOf(filmId);
        model.addAttribute("movie", movieService.findById(filmIdLong));
        model.addAttribute("moviePrograms", movieService.programsForFilm(filmIdLong));
        model.addAttribute("numberOfSeats", 1);
        return "movie/index";
    }

    @PostMapping("/bookings")
    public String bookings(@RequestParam(value = "programId") String programId, @RequestParam(value = "numberOfSeats") String numberOfSeats) {
        bookingService.makeBookingFor(Long.valueOf(programId), Integer.valueOf(numberOfSeats));
        return "redirect:/bookings";
    }

    @GetMapping("/bookings")
    public String bookings(Model model) {
        model.addAttribute("bookings", bookingService.findAll());
        return "bookings/index";
    }

    @DeleteMapping("/bookings/{bookingId}")
    public String deleteBooking(@PathVariable(value = "bookingId") Long bookingId) {
        bookingService.cancelBookingFor(bookingId);

        return "redirect:/bookings";
    }
}
