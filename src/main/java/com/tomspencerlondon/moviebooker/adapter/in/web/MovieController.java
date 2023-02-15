package com.tomspencerlondon.moviebooker.adapter.in.web;

import com.tomspencerlondon.moviebooker.hexagon.application.BookingService;
import com.tomspencerlondon.moviebooker.hexagon.application.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        model.addAttribute("moviePrograms", movieService.programsForFilm(Long.valueOf(filmId)));

        return "movie/index";
    }

    @PostMapping("/bookings")
    public String bookings(@RequestParam(value = "programId", defaultValue = "") String programId) {
        bookingService.makeBookingFor(Long.valueOf(programId));
        return "redirect:/bookings";
    }

    @GetMapping("/bookings")
    public String bookings(Model model) {
        return "bookings/index";
    }
}
