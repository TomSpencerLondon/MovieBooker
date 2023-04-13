package com.tomspencerlondon.moviebooker.admin.adapter.out.jpa;


import com.tomspencerlondon.moviebooker.admin.hexagon.application.port.ProgramRepository;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.Program;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProgramRepositoryJpaAdapter implements ProgramRepository {

    private final ProgramJpaRepository programJpaRepository;
    private final ProgramTransformer programTransformer;

    public ProgramRepositoryJpaAdapter(ProgramJpaRepository programJpaRepository,
                                       ProgramTransformer programTransformer) {
        this.programJpaRepository = programJpaRepository;
        this.programTransformer = programTransformer;
    }

    @Override
    public List<Program> findAll() {

        return programJpaRepository.findAll()
                .stream().map(programTransformer::toMovieProgram).toList();
    }
}

