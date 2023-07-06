package com.tomspencerlondon.moviebooker.admin.adapter.in.web;

import com.tomspencerlondon.moviebooker.admin.hexagon.application.AdminImageUploadService;
import com.tomspencerlondon.moviebooker.admin.hexagon.application.AdminMovieService;
import com.tomspencerlondon.moviebooker.admin.hexagon.application.AdminProgramService;
import com.tomspencerlondon.moviebooker.admin.hexagon.application.ScreenService;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminMovie;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminProgram;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.Screen;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.File;

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
    private ScreenService screenService;

    public AdminController(AdminProgramService adminProgramService, AdminMovieService adminMovieService, AdminImageUploadService adminImageUploadService, ScreenService screenService) {
        this.adminProgramService = adminProgramService;
        this.adminMovieService = adminMovieService;
        this.adminImageUploadService = adminImageUploadService;
        this.screenService = screenService;
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
        List<Screen> screens = screenService.findAll();
        AddProgramForm addProgramForm = new AddProgramForm();
        List<AdminMovieView> adminMovieViews = adminMovieService
                .findAll().stream().map(AdminMovieView::from).toList();
        addProgramForm.setAdminMovies(adminMovieViews);
        addProgramForm.setScreens(screens);

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
        Long selectedScreenId = addProgramForm.getSelectedScreenId();

        Screen selectedScreen = screenService.findById(selectedScreenId);
        AdminProgram adminProgram = new AdminProgram(null,
                scheduleDate,
                selectedScreen,
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
        File uploadedFile;
        try {
            uploadedFile = adminImageUploadService.uploadObjectToS3(movieImage.getOriginalFilename(), movieImage.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        AdminMovie adminMovie = new AdminMovie(null, addMovieForm.getMovieName(), uploadedFile.fileUrl(), addMovieForm.getReleaseDate(), addMovieForm.getDescription());

        adminMovieService.save(adminMovie);

        return "redirect:/admin/movie-list";
    }
}
