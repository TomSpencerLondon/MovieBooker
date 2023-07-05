package com.tomspencerlondon.moviebooker.common.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.admin.adapter.out.jpa.ScreenTransformer;
import com.tomspencerlondon.moviebooker.common.hexagon.application.port.ScreenRepository;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.Screen;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScreenRepositoryJpaAdapter implements ScreenRepository {
    private final ScreenJpaRepository screenJpaRepository;
    private final ScreenTransformer screenTransformer = new ScreenTransformer();

    public ScreenRepositoryJpaAdapter(ScreenJpaRepository screenJpaRepository) {
        this.screenJpaRepository = screenJpaRepository;
    }

    public List<Screen> findAll() {
        return screenJpaRepository.findAll()
                .stream().map(screenTransformer::toScreen).toList();
    }

    @Override
    public Screen findById(Long id) {
        ScreenDbo screenDbo = screenJpaRepository.findById(id)
            .orElseThrow(UnsupportedOperationException::new);
        Screen screen = screenTransformer.toScreen(screenDbo);
        return screen;
    }

    @Override
    public Screen save(Screen screen) {
        ScreenDbo screenDbo = screenTransformer.toScreenDbo(screen);
        return screenTransformer.toScreen(screenJpaRepository.save(screenDbo));
    }
}
