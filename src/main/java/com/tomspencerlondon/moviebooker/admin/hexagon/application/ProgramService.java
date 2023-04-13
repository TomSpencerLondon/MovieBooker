package com.tomspencerlondon.moviebooker.admin.hexagon.application;

import com.tomspencerlondon.moviebooker.admin.hexagon.application.port.ProgramRepository;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.Program;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramService {

    private final ProgramRepository programRepository;

    public ProgramService(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    public List<Program> findAll() {
        return programRepository.findAll();
    }
}
