package com.tomspencerlondon.moviebooker.common.hexagon.application.port;

import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminProgram;

import java.util.List;

public interface AdminMovieProgramRepository {
    List<AdminProgram> findAll();

    void save(AdminProgram adminProgram);
}
