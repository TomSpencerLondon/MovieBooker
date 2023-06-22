package com.tomspencerlondon.moviebooker.common.utils;

import java.util.Optional;

public class Optionals {
    public static <T> Optional<T> or(Optional<T> optional, Optional<T> fallback) {
        return optional.isPresent() ? optional : fallback;
    }
}
