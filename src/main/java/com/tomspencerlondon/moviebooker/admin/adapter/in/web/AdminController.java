package com.tomspencerlondon.moviebooker.admin.adapter.in.web;

import com.tomspencerlondon.moviebooker.admin.hexagon.application.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private ProgramService programService;

    @GetMapping("/movie-programs")
    public String allPrograms(Model model) {
        model.addAttribute("programs", programService.findAll());
        return "admin/program/programs";
    }
}
