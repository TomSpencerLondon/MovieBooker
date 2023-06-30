package com.tomspencerlondon.moviebooker.admin.hexagon.application.port;

import com.tomspencerlondon.moviebooker.admin.hexagon.domain.Screen;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.ScreenDbo;

public class ScreenTransformer {
    public Screen toScreen(ScreenDbo screenDbo) {
        return new Screen(screenDbo.getScreenId(), screenDbo.getNumberOfSeats());
    }
}
