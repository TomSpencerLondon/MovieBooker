package com.tomspencerlondon.moviebooker;

import com.tomspencerlondon.moviebooker.admin.hexagon.application.AdminMovieService;
import com.tomspencerlondon.moviebooker.admin.hexagon.application.AdminProgramService;
import com.tomspencerlondon.moviebooker.admin.hexagon.application.AdminUserService;
import com.tomspencerlondon.moviebooker.admin.hexagon.application.ScreenService;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminMovie;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminProgram;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminUser;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.Screen;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.MovieGoer;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@SpringBootApplication
public class MovieBookerApplication implements CommandLineRunner {
    @Autowired
    AdminMovieService movieService;

    @Autowired
    AdminProgramService adminProgramService;

    @Autowired
    ScreenService screenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminUserService adminUserService;

    public static void main(String[] args) {
        SpringApplication.run(MovieBookerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        bootstrapData();
    }

    private void bootstrapData() {
        String description = "Widely regarded as one of the greatest films of all time, this mob drama, based on Mario Puzo's novel of the same name, focuses on the powerful Italian-American crime family of Don Vito Corleone (Marlon Brando). When the don's youngest son, Michael (Al Pacino), reluctantly joins the Mafia, he becomes involved in the inevitable cycle of violence and betrayal. Although Michael tries to maintain a normal relationship with his wife, Kay (Diane Keaton), he is drawn deeper into the family business.";
        AdminMovie movie = new AdminMovie(1L,
                "Godfather",
                "/img/godfather.jpg",
                LocalDate.of(1975, 8, 24)
                , description);

        Screen screen = new Screen(null,20, "Screen 1");

        Screen savedScreen = screenService.save(screen);

        AdminMovie savedMovie = movieService.save(movie);
        var movieProgram = new AdminProgram(1L,
                LocalDateTime.of(2023, 2, 14, 13, 0),
                savedScreen,
                savedMovie, new BigDecimal("5.00"));

        adminProgramService.save(movieProgram);

        AdminUser adminUser = new AdminUser(null,
                "tom.spencer.admin@gmail.com",
                passwordEncoder.encode("password"), Role.ADMIN);

        adminUserService.save(adminUser);

    }
}
