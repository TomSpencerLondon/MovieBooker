package com.tomspencerlondon.moviebooker.adapter.in.web;

import com.tomspencerlondon.moviebooker.hexagon.application.BookingService;
import com.tomspencerlondon.moviebooker.hexagon.application.MovieGoerService;
import com.tomspencerlondon.moviebooker.hexagon.application.MovieService;
import com.tomspencerlondon.moviebooker.hexagon.domain.Booking;
import com.tomspencerlondon.moviebooker.hexagon.domain.MovieGoer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MovieController {

    private final MovieService movieService;
    private final BookingService bookingService;
    private MovieGoerService movieGoerService;

    private PasswordEncoder passwordEncoder;

    public MovieController(MovieService movieService, BookingService bookingService, MovieGoerService movieGoerService, PasswordEncoder passwordEncoder) {
        this.movieService = movieService;
        this.bookingService = bookingService;
        this.movieGoerService = movieGoerService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("movieGoer", movieGoerView());
        model.addAttribute("movies", movieService.findAll());
        return "start";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        MovieGoerRegistrationForm movieGoerRegistrationForm = new MovieGoerRegistrationForm();
        model.addAttribute("movieGoerRegistrationForm", movieGoerRegistrationForm);
        return "registration";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("movieGoerRegistrationForm") MovieGoerRegistrationForm movieGoerRegistrationForm, Model model) {
        MovieGoer movieGoer = new MovieGoer(
                movieGoerRegistrationForm.getUserName(),
                passwordEncoder.encode(movieGoerRegistrationForm.getPassword()),
                0);
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
        model.addAttribute("movieGoer", movieGoerView());
        model.addAttribute("movie", movieService.findById(filmIdLong));
        model.addAttribute("moviePrograms", movieService.programsForFilm(filmIdLong));
        model.addAttribute("numberOfSeats", 1);
        return "movie/index";
    }

    @GetMapping("/book")
    public String createBooking(Model model, @RequestParam(value = "movieProgramId")
    Long movieProgramId, @RequestParam(value = "numberOfSeats") int numberOfSeats) {
        Booking booking = bookingService.createBooking(movieGoerView().getUserName(), movieProgramId, numberOfSeats);
        BookingForm bookingForm = BookingForm.from(booking);
        model.addAttribute("bookingForm", bookingForm);
        model.addAttribute("movieGoer", movieGoerView());
        return "bookings/book";
    }

    @PostMapping("/book")
    public String makeBooking(@ModelAttribute("bookingForm") BookingForm bookingForm) {
        Booking booking = BookingForm.to(bookingForm);
        bookingService.save(booking);
        return "redirect:/bookings";
    }

    @PostMapping("/bookings")
    public String bookings(@RequestParam(value = "programId") Long programId,
                           @RequestParam(value = "numberOfSeats") int numberOfSeats) {
        return "redirect:/book?movieProgramId=" + programId + "&numberOfSeats=" + numberOfSeats;
    }

    @GetMapping("/bookings")
    public String bookings(Model model) {
        model.addAttribute("bookings", bookingService.findAllBookingsFor(movieGoerView().getUserName()));
        model.addAttribute("movieGoer", movieGoerView());
        return "bookings/index";
    }

    private MovieGoerView movieGoerView() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userPrincipal = (UserDetails)authentication.getPrincipal();
        return MovieGoerView.from(movieGoerService.findByUserName(userPrincipal.getUsername()));
    }

}
