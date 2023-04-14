package com.tomspencerlondon.moviebooker.admin.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminProgram;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieProgramJpaRepository;
import com.tomspencerlondon.moviebooker.common.hexagon.application.port.AdminMovieProgramRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminProgramRepositoryJpaAdapter implements AdminMovieProgramRepository {

    private final MovieProgramJpaRepository programJpaRepository;
    private final ProgramTransformer programTransformer;

    public AdminProgramRepositoryJpaAdapter(MovieProgramJpaRepository programJpaRepository,
                                            ProgramTransformer programTransformer) {
        this.programJpaRepository = programJpaRepository;
        this.programTransformer = programTransformer;
    }

    @Override
    public List<AdminProgram> findAll() {

        return programJpaRepository.findAll()
                .stream().map(programTransformer::toMovieProgram).toList();
    }
}

