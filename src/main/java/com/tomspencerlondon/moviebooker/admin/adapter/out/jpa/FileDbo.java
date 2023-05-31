package com.tomspencerlondon.moviebooker.admin.adapter.out.jpa;

import jakarta.persistence.*;

@Entity
@Table(name = "file_info")
public class FileDbo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String fileName;

    @Column(length = 1000)
    private String fileUrl;

    private boolean isUploadSuccessFull;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public boolean isUploadSuccessFull() {
        return isUploadSuccessFull;
    }

    public void setUploadSuccessFull(boolean uploadSuccessFull) {
        isUploadSuccessFull = uploadSuccessFull;
    }
}
