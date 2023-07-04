package com.tomspencerlondon.moviebooker.common.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.admin.adapter.out.jpa.ScreenTransformer;
import com.tomspencerlondon.moviebooker.common.hexagon.application.port.ScreenRepository;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.Screen;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScreenRepositoryJpaAdapter implements ScreenRepository {
    private final ScreenJpaRepository screenJpaRepository;

    public ScreenRepositoryJpaAdapter(ScreenJpaRepository screenJpaRepository) {
        this.screenJpaRepository = screenJpaRepository;
    }

    public List<Screen> findAll() {
        return screenJpaRepository.findAll()
                .stream().map(this::toScreen).toList();
    }

    @Override
    public Screen findById(Long id) {
        ScreenDbo screenDbo = screenJpaRepository.findById(id)
            .orElseThrow(UnsupportedOperationException::new);
        ScreenTransformer screenTransformer = new ScreenTransformer();
        Screen screen = screenTransformer.toScreen(screenDbo);
        return screen;
    }

    private Screen toScreen(ScreenDbo screenDbo) {
        return new Screen(screenDbo.getScreenId(), screenDbo.getNumberOfSeats());
    }

}
