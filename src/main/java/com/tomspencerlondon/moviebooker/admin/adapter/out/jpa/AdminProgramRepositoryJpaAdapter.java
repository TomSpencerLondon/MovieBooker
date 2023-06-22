package com.tomspencerlondon.moviebooker.admin.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminProgram;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieProgramDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieProgramJpaRepository;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.ScreenDbo;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.ScreenJpaRepository;
import com.tomspencerlondon.moviebooker.common.hexagon.application.port.AdminMovieProgramRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminProgramRepositoryJpaAdapter implements AdminMovieProgramRepository {

    private final MovieProgramJpaRepository programJpaRepository;
    private final ScreenJpaRepository screenJpaRepository;
    private final ProgramTransformer programTransformer;

    public AdminProgramRepositoryJpaAdapter(MovieProgramJpaRepository programJpaRepository,
                                            ScreenJpaRepository screenJpaRepository, ProgramTransformer programTransformer) {
        this.programJpaRepository = programJpaRepository;
        this.screenJpaRepository = screenJpaRepository;
        this.programTransformer = programTransformer;
    }

    @Override
    public List<AdminProgram> findAll() {
        return programJpaRepository.findAll()
                .stream().map(this::screenAndCreateMovieProgram).toList();
    }

    @Override
    public void save(AdminProgram adminProgram) {
        programJpaRepository.save(programTransformer.fromMovieProgram(adminProgram));
    }

    private AdminProgram screenAndCreateMovieProgram(MovieProgramDbo movieProgramDbo) {
        ScreenDbo screenDbo = screenJpaRepository
                .findById(movieProgramDbo.getScreenId())
                .orElseThrow(IllegalArgumentException::new);

        return programTransformer.toMovieProgram(movieProgramDbo, screenDbo);
    }
}

