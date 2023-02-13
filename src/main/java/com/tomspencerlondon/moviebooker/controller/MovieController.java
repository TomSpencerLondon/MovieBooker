package com.tomspencerlondon.moviebooker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MovieController {
    @GetMapping("/")
    public String home() {
        return "start";
    }
}
