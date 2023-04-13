package com.tomspencerlondon.moviebooker.moviegoer.hexagon.application;

import java.util.ArrayList;
import java.util.List;

public class Notification {
    private final List<String> errors = new ArrayList<>();

    public Notification() {
    }

    public boolean isSuccess() {
        return errors.isEmpty();
    }

    public void addError(String message) {
        errors.add(message);
    }
}
