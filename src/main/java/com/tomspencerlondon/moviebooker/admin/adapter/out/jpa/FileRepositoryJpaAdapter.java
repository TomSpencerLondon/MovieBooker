package com.tomspencerlondon.moviebooker.admin.adapter.out.jpa;


import com.tomspencerlondon.moviebooker.admin.hexagon.application.port.FileRepository;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.File;
import com.tomspencerlondon.moviebooker.common.adapter.out.jpa.MovieGoerJpaRepository;
import com.tomspencerlondon.moviebooker.common.hexagon.application.port.MovieGoerRepository;
import org.springframework.stereotype.Repository;

@Repository
public class FileRepositoryJpaAdapter implements FileRepository {
    private final FileJpaRepository fileJpaRepository;

    public FileRepositoryJpaAdapter(FileJpaRepository fileJpaRepository) {
        this.fileJpaRepository = fileJpaRepository;
    }

    @Override
    public File save(File file) {
        FileDbo fileDbo = new FileDbo();
        fileDbo.setFileName(file.fileName());
        fileDbo.setFileUrl(file.fileUrl());

        FileDbo savedFileDbo = fileJpaRepository.save(fileDbo);

        return new File(savedFileDbo.getId(), savedFileDbo.getFileName(), savedFileDbo.getFileUrl(), fileDbo.isUploadSuccessFull());
    }
}
