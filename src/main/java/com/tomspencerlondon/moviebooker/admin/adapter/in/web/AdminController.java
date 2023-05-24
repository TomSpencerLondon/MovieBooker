package com.tomspencerlondon.moviebooker.admin.adapter.in.web;

import com.tomspencerlondon.moviebooker.admin.hexagon.application.AdminMovieService;
import com.tomspencerlondon.moviebooker.admin.hexagon.application.AdminProgramService;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminMovie;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminProgram;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private AdminProgramService adminProgramService;
    private AdminMovieService adminMovieService;

    public AdminController(AdminProgramService adminProgramService, AdminMovieService adminMovieService) {
        this.adminProgramService = adminProgramService;
        this.adminMovieService = adminMovieService;
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
}
