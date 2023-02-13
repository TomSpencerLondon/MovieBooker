package com.tomspencerlondon.moviebooker.adapter.in.web;

import com.tomspencerlondon.moviebooker.hexagon.application.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("movies", movieService.findAll());
        return "start";
    }
}
