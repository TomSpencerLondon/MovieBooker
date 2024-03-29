package com.tomspencerlondon.moviebooker.moviegoer.adapter.in.web;

import com.tomspencerlondon.moviebooker.common.hexagon.ImageService;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.application.BookingService;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.application.MovieGoerService;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.application.MovieService;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.application.Notification;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.*;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/moviegoer")
public class MovieController {

    private final MovieService movieService;
    private final BookingService bookingService;
    private final MovieGoerService movieGoerService;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    public MovieController(MovieService movieService, BookingService bookingService, MovieGoerService movieGoerService, PasswordEncoder passwordEncoder,
        ImageService imageService) {
        this.movieService = movieService;
        this.bookingService = bookingService;
        this.movieGoerService = movieGoerService;
        this.passwordEncoder = passwordEncoder;
        this.imageService = imageService;
    }

    @GetMapping("/403")
    public String _403(Model model) {
        List<MovieView> nowShowing = movieService.nowShowing()
                .stream()
                .map(movie -> MovieView.from(movie, imageService.imagePath(movie.image())))
                .toList();
        List<MovieView> comingSoon = movieService.comingSoon()
            .stream()
            .map(movie -> MovieView.from(movie, imageService.imagePath(movie.image())))
            .toList();
        model.addAttribute("nowShowing", nowShowing);
        model.addAttribute("comingSoon", comingSoon);
        return "moviegoer/403";
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("movieGoer", movieGoerView());
        List<MovieView> movies = movieService.findAll()
                .stream().map(movie -> MovieView.from(movie, imageService.imagePath(movie.image())))
                .toList();
        model.addAttribute("movies", movies);
        return "moviegoer/start";
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "error", defaultValue = "false") boolean error, Model model) {
        model.addAttribute("error", error);
        return "moviegoer/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        MovieGoerRegistrationForm movieGoerRegistrationForm = new MovieGoerRegistrationForm();
        model.addAttribute("movieGoerRegistrationForm", movieGoerRegistrationForm);
        return "moviegoer/registration";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("movieGoerRegistrationForm") MovieGoerRegistrationForm movieGoerRegistrationForm, Model model) {
        // TODO: is the right place for the passwordEncoder? Should the passwordEncoder be used in the SecurityConfig
        MovieGoer movieGoer = new MovieGoer(
                null, null, movieGoerRegistrationForm.getUserName(),
                passwordEncoder.encode(movieGoerRegistrationForm.getPassword()),
                0, false, false,
                Role.USER
        );
        try {
            movieGoerService.save(movieGoer);
            // TODO: We could change this to a more specific exception
        } catch (RuntimeException e) {
            model.addAttribute("movieGoerRegistrationForm", movieGoerRegistrationForm);
            return "moviegoer/registration";
        }
        return "redirect:/moviegoer/";
    }

    @GetMapping("/movie")
    public String movie(Model model, @RequestParam(value = "filmId", defaultValue = "") String filmId) {
        Long filmIdLong = Long.valueOf(filmId);
        model.addAttribute("movieGoer", movieGoerView());
        Movie movie = movieService.findById(filmIdLong);
        model.addAttribute("movie", MovieView.from(movie,
            imageService.imagePath(movie.image())));
        List<ProgramView> moviePrograms = movieService.programsForFilm(filmIdLong)
                .stream()
                .map(program -> ProgramView.from(
                    program,
                    MovieView.from(program.movie(), imageService.imagePath(program.movie().image()))
                )).toList();
        model.addAttribute("moviePrograms", moviePrograms);
        model.addAttribute("numberOfSeats", 1);
        return "moviegoer/movie/index";
    }

    @GetMapping("/book")
    public String createBooking(Model model, @RequestParam(value = "movieProgramId")
    Long movieProgramId, @RequestParam(value = "numberOfSeats") int numberOfSeats) {
        MovieGoerView movieGoerView = movieGoerView();
        if (movieGoerView.isAskedForLoyalty()) {
            // TODO: should these functions be combined in the BookingService? - Perhaps as a BookingTransaction
            Booking booking = bookingService.createBooking(movieGoerView.getUserName(), movieProgramId, numberOfSeats);
            Payment payment = bookingService.calculatePayment(booking);
            BookingForm bookingForm = BookingForm.from(booking, payment);
            model.addAttribute("bookingForm", bookingForm);
            model.addAttribute("movieGoer", movieGoerView);
            return "moviegoer/bookings/book";
        } else {
            return "redirect:/moviegoer/loyalty-signup?programId=" + movieProgramId + "&numberOfSeats=" + numberOfSeats;
        }
    }

    @PostMapping("/book")
    public String makeBooking(@ModelAttribute("bookingForm") BookingForm bookingForm) {
        MovieProgram movieProgram = movieService.findMovieProgramBy(bookingForm.getScheduleId());
        Booking booking = BookingForm.to(bookingForm, movieProgram);
        Payment payment = BookingForm.toPayment(bookingForm);
        Notification notification = bookingService.payForBooking(booking, payment);

        if (notification.isSuccess()) {
            return "redirect:/moviegoer/bookings";
        } else {
            return "redirect:/moviegoer/seatsNotAvailable";
        }
    }



    @GetMapping("/amendBooking")
    public String amendBooking(Model model, @RequestParam(value = "bookingId") Long bookingId, @RequestParam(value = "numberOfSeats") int numberOfSeats) {
        AmendBookingTransaction amendTransaction = bookingService.createAmendTransaction(bookingId, numberOfSeats);
        AmendBookingForm amendBookingForm = AmendBookingForm.from(amendTransaction, amendTransaction.payment());
        MovieGoerView movieGoerView = movieGoerView();
        model.addAttribute("amendBookingForm", amendBookingForm);
        model.addAttribute("movieGoer", movieGoerView);

        return "moviegoer/bookings/amend";
    }

    @PostMapping("/amendBooking")
    public String makeAmendBooking(@ModelAttribute("amendBookingForm") AmendBookingForm amendBookingForm,
                                   @RequestParam("bookingId") Long bookingId,
                                   @RequestParam("additionalSeats") int additionalSeats) {
        Payment payment = AmendBookingForm.toPayment(amendBookingForm);
        Notification notification = bookingService.amendBooking(bookingId, additionalSeats, payment);

        if(notification.isSuccess()) {
            return "redirect:/moviegoer/bookings";
        } else {
            return "redirect:/moviegoer/seatsNotAvailable?bookingId=" + bookingId;
        }
    }

    @GetMapping("/seatsNotAvailable")
    public String seatsNotAvailable(Model model, @RequestParam(value = "bookingId", defaultValue = "-1") Long bookingId) {
        // TODO: We could add a BookingId class which would contain validation
        if (bookingId != -1) {
            Booking booking = bookingService.findBooking(bookingId);
            BookingForm bookingForm = BookingForm.from(booking);
            model.addAttribute("bookingForm", bookingForm);
        }

        model.addAttribute("movieGoer", movieGoerView());
        return "moviegoer/bookings/seatsNotAvailable";
    }

    @PostMapping("/bookings")
    public String bookings(@RequestParam(value = "programId") Long programId,
                           @RequestParam(value = "numberOfSeats") int numberOfSeats) {
        return "redirect:/moviegoer/book?movieProgramId=" + programId + "&numberOfSeats=" + numberOfSeats;
    }

    @GetMapping("/bookings")
    public String bookings(Model model) {
        model.addAttribute("bookings", bookingService.findAllBookingsFor(movieGoerView().getUserName()));
        model.addAttribute("movieGoer", movieGoerView());
        return "moviegoer/bookings/index";
    }

    @GetMapping("/loyalty-signup")
    public String loyaltySignUp(Model model, @RequestParam(value = "programId") Long programId,
                                @RequestParam(value = "numberOfSeats") int numberOfSeats) {
        model.addAttribute("movieGoer", movieGoerView());
        model.addAttribute("programId", programId);
        model.addAttribute("numberOfSeats", numberOfSeats);
        return "moviegoer/loyalty/signup";
    }

    @PostMapping("/loyalty-signup")
    public String loyaltySignUp(@RequestParam(value = "optIn") boolean optIn,
                                @RequestParam(value = "programId") Long programId,
                                @RequestParam(value = "numberOfSeats") int numberOfSeats) {
        MovieGoerView movieGoerView = movieGoerView();
        if (optIn) {
            movieGoerService.optIntoLoyaltyScheme(movieGoerView.getUserName());
        } else {
            movieGoerService.optOutOfLoyaltyScheme(movieGoerView.getUserName());
        }

        return "redirect:/moviegoer/book?movieProgramId=" + programId + "&numberOfSeats=" + numberOfSeats;
    }

    @PostMapping("/loyalty-signup-confirmation")
    public String loyaltySignUp(Model model) {
        MovieGoerView movieGoerView = movieGoerView();
        movieGoerService.optIntoLoyaltyScheme(movieGoerView.getUserName());
        model.addAttribute("movieGoer", movieGoerView);

        return "redirect:/moviegoer/loyalty-signup-confirmation";
    }

    @GetMapping("/loyalty-signup-confirmation")
    public String loyaltySignUpConfirmation(Model model) {
        MovieGoerView movieGoerView = movieGoerView();
        model.addAttribute("movieGoer", movieGoerView);

        return "moviegoer/loyalty/signup-confirmation";
    }

    private MovieGoerView movieGoerView() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        return MovieGoerView.from(movieGoerService.findByUserName(userPrincipal.getUsername()));
    }

}
