package com.tomspencerlondon.moviebooker.admin.hexagon.application;

import com.tomspencerlondon.moviebooker.common.hexagon.application.port.ScreenRepository;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.Screen;
import org.springframework.stereotype.Service;

import java.util.List;

public class ScreenService {

    private final ScreenRepository screenRepository;

    public ScreenService(ScreenRepository screenRepository) {
        this.screenRepository = screenRepository;
    }

    public List<Screen> findAll() {
        return screenRepository.findAll();
    }

    public Screen save(Screen screen) {
        return screenRepository.save(screen);
    }
}
