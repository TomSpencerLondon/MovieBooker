package com.tomspencerlondon.moviebooker.admin.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.admin.hexagon.domain.Screen;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.ScreenDbo;


public class ScreenTransformer {
    public Screen toScreen(ScreenDbo screenDbo) {
        return new Screen(screenDbo.getScreenId(), screenDbo.getNumberOfSeats(), screenDbo.getName());
    }

    public ScreenDbo toScreenDbo(Screen screen) {
        ScreenDbo screenDbo = new ScreenDbo();
        screenDbo.setScreenId(screen.getId());
        screenDbo.setNumberOfSeats(screen.numberOfSeats());
        screenDbo.setName(screen.name());
        return screenDbo;
    }
}
