package com.tomspencerlondon.moviebooker.admin.hexagon.application;

import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminProgram;
import com.tomspencerlondon.moviebooker.common.hexagon.application.port.AdminMovieProgramRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminProgramService {

    private final AdminMovieProgramRepository movieProgramRepository;

    public AdminProgramService(AdminMovieProgramRepository movieProgramRepository) {
        this.movieProgramRepository = movieProgramRepository;
    }

    public List<AdminProgram> findAll() {
        return movieProgramRepository.findAll();
    }

    public void save(AdminProgram adminProgram) {
        movieProgramRepository.save(adminProgram);
    }
}
