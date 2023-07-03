package com.tomspencerlondon.moviebooker.admin.config;

import com.tomspencerlondon.moviebooker.admin.hexagon.application.AdminMovieService;
import com.tomspencerlondon.moviebooker.admin.hexagon.application.AdminProgramService;
import com.tomspencerlondon.moviebooker.common.hexagon.application.port.AdminMovieProgramRepository;
import com.tomspencerlondon.moviebooker.common.hexagon.application.port.AdminMovieRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminMovieConfig {

  @Bean
  AdminMovieService adminMovieService(AdminMovieRepository adminMovieService) {
    return new AdminMovieService(adminMovieService);
  }

  @Bean
  AdminProgramService adminProgramService(AdminMovieProgramRepository movieProgramRepository) {
    return new AdminProgramService(movieProgramRepository);
  }
}
