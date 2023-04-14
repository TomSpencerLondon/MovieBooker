package com.tomspencerlondon.moviebooker.admin.adapter.in.web;

import com.tomspencerlondon.moviebooker.admin.hexagon.application.AdminProgramService;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.application.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private AdminProgramService adminProgramService;

    @Autowired
    private MovieService movieService;

    @GetMapping("/movie-programs")
    public String allPrograms(Model model) {
        model.addAttribute("programs", adminProgramService.findAll());
        return "admin/program/programs";
    }

    @GetMapping("/add-programs")
    public String addPrograms(Model model) {
        model.addAttribute("addProgramForm", new AddProgramForm());

        return "admin/program/add-program";
    }
}
