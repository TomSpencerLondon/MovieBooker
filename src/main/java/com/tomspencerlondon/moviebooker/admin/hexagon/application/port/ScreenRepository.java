package com.tomspencerlondon.moviebooker.admin.hexagon.application.port;

import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminProgram;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.Screen;

import java.util.List;

public interface ScreenRepository {
    List<Screen> findAll();
}
