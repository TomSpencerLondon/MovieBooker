package com.tomspencerlondon.moviebooker.admin.adapter.in.web;

import com.tomspencerlondon.moviebooker.admin.hexagon.application.AdminImageUploadService;
import com.tomspencerlondon.moviebooker.admin.hexagon.application.AdminMovieService;
import com.tomspencerlondon.moviebooker.admin.hexagon.application.AdminProgramService;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminMovie;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminProgram;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private AdminProgramService adminProgramService;
    private AdminMovieService adminMovieService;
    private AdminImageUploadService adminImageUploadService;

    public AdminController(AdminProgramService adminProgramService, AdminMovieService adminMovieService, AdminImageUploadService adminImageUploadService) {
        this.adminProgramService = adminProgramService;
        this.adminMovieService = adminMovieService;
        this.adminImageUploadService = adminImageUploadService;
    }

    @GetMapping("/movie-programs")
    public String allPrograms(Model model) {
        List<AdminProgramView> adminProgramViews = adminProgramService.findAll()
                .stream().map(AdminProgramView::from)
                .toList();

        model.addAttribute("programs", adminProgramViews);
        return "admin/program/programs";
    }

    @GetMapping("/add-program")
    public String addPrograms(Model model) {
        AddProgramForm addProgramForm = new AddProgramForm();
        List<AdminMovieView> adminMovieViews = adminMovieService
                .findAll().stream().map(AdminMovieView::from).toList();
        addProgramForm.setAdminMovies(adminMovieViews);

        model.addAttribute("addProgramForm", addProgramForm);

        return "admin/program/add-program";
    }

    @PostMapping("/add-program")
    public String addProgram(@Valid @ModelAttribute("addProgramForm") AddProgramForm addProgramForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<AdminMovieView> adminMovieViews = adminMovieService
                    .findAll().stream().map(AdminMovieView::from).toList();
            addProgramForm.setAdminMovies(adminMovieViews);
            return "/admin/program/add-program";
        }

        AdminMovie adminMovie = adminMovieService.findById(addProgramForm.getMovieId());
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("MMM d, yyyy h:m a", Locale.US);

        LocalDateTime scheduleDate = LocalDateTime.parse(addProgramForm.getScheduleDate(), formatter);
        AdminProgram adminProgram = new AdminProgram(null,
                scheduleDate,
                addProgramForm.getSeats(),
                adminMovie,
                addProgramForm.getPrice());

        adminProgramService.save(adminProgram);

        return "redirect:/admin/movie-programs";
    }

    @GetMapping("/movie-list")
    public String home(Model model) {
        model.addAttribute("movies", adminMovieService.findAll());
        return "admin/movie/movie-list";
    }

    @GetMapping("/add-movie")
    public String addMovie(Model model) {
        AddMovieForm addMovieForm = new AddMovieForm();
        model.addAttribute("addMovieForm", addMovieForm);

        return "admin/movie/add-movie";
    }

    @PostMapping("add-movie")
    public String createMovie(@Valid @ModelAttribute("addMovieForm") AddMovieForm addMovieForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin/add-movie";
        }
        MultipartFile movieImage = addMovieForm.getMovieImage();
        try {
            adminImageUploadService.uploadObjectToS3(movieImage.getOriginalFilename(), movieImage.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return "redirect:/admin/movie-list";
    }
}
