package com.tomspencerlondon.moviebooker.admin.hexagon.application.port;

import com.tomspencerlondon.moviebooker.admin.hexagon.domain.File;

public interface FileRepository {
    File save(File file);
}
