package com.tomspencerlondon.moviebooker.common.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.admin.hexagon.application.port.ScreenRepository;
import com.tomspencerlondon.moviebooker.admin.adapter.out.jpa.ScreenTransformer;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.Screen;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ScreenRepositoryJpaAdapter implements ScreenRepository {
    private ScreenJpaRepository screenJpaRepository;

    public ScreenRepositoryJpaAdapter(ScreenJpaRepository screenJpaRepository) {
        this.screenJpaRepository = screenJpaRepository;
    }

    @Override
    public List<Screen> findAll() {
        return null;
    }

    @Override
    public Screen findById(Long id) {
        ScreenDbo screenDbo = screenJpaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        ScreenTransformer screenTransformer = new ScreenTransformer();
        Screen screen = screenTransformer.toScreen(screenDbo);

        return screen;
    }
}
