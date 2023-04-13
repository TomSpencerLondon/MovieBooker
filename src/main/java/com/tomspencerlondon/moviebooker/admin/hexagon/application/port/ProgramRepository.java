package com.tomspencerlondon.moviebooker.admin.hexagon.application.port;

import com.tomspencerlondon.moviebooker.admin.hexagon.domain.Program;

import java.util.List;

public interface ProgramRepository {
    List<Program> findAll();
}

