package com.tomspencerlondon.moviebooker.admin.adapter.in.web;

import com.tomspencerlondon.moviebooker.admin.hexagon.application.AdminMovieService;
import com.tomspencerlondon.moviebooker.admin.hexagon.application.AdminProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private AdminProgramService adminProgramService;

    @Autowired
    private AdminMovieService adminMovieService;

    @GetMapping("/movie-programs")
    public String allPrograms(Model model) {
        model.addAttribute("programs", adminProgramService.findAll());
        return "admin/program/programs";
    }

    @GetMapping("/add-programs")
    public String addPrograms(Model model) {
        AddProgramForm addProgramForm = new AddProgramForm();
        List<AdminMovieView> adminMovieViews = adminMovieService
                .findAll().stream().map(AdminMovieView::from).toList();
        addProgramForm.setAdminMovies(adminMovieViews);

        model.addAttribute("addProgramForm", addProgramForm);

        return "admin/program/add-program";
    }
}