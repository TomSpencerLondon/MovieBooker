package com.tomspencerlondon.moviebooker.admin.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.admin.hexagon.application.port.ScreenRepository;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminProgram;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.Screen;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.ScreenDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.ScreenJpaRepository;
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

        return new Screen(screenDbo.getScreenId(), screenDbo.getNumberOfSeats());
    }

    private Screen toScreen(ScreenDbo screenDbo) {
        return new Screen(screenDbo.getScreenId(), screenDbo.getNumberOfSeats());
    }

}
