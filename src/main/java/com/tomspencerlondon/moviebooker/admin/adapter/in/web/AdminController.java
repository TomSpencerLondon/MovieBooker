package com.tomspencerlondon.moviebooker.admin.adapter.in.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/admin")
public class AdminController {
    @GetMapping("/movie-programs")
    public String home(Model model) {
        return "movie-programs";
    }
}
