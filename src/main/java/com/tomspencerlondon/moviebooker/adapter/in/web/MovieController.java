package com.tomspencerlondon.moviebooker.adapter.in.web;

import com.tomspencerlondon.moviebooker.hexagon.application.BookingService;
import com.tomspencerlondon.moviebooker.hexagon.application.MovieGoerService;
import com.tomspencerlondon.moviebooker.hexagon.application.MovieService;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieGoer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MovieController {

    private final MovieService movieService;
    private final BookingService bookingService;
    private MovieGoerService movieGoerService;

    public MovieController(MovieService movieService, BookingService bookingService, MovieGoerService movieGoerService) {
        this.movieService = movieService;
        this.bookingService = bookingService;
        this.movieGoerService = movieGoerService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("movies", movieService.findAll());
        return "start";
    }

    @GetMapping("/register")
    public String register(Model model) {
        MovieGoerRegistrationForm movieGoerRegistrationForm = new MovieGoerRegistrationForm();
        model.addAttribute("movieGoerRegistrationForm", movieGoerRegistrationForm);
        return "registration";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("movieGoerRegistrationForm") MovieGoerRegistrationForm movieGoerRegistrationForm, Model model) {
        MovieGoer movieGoer = new MovieGoer(movieGoerRegistrationForm.getUserName(), movieGoerRegistrationForm.getPassword(), 0);
        try {
            movieGoerService.save(movieGoer);
        } catch (RuntimeException e) {
            model.addAttribute("movieGoerRegistrationForm", movieGoerRegistrationForm);
            return "registration";
        }
        return "redirect:/";
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
