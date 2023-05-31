package com.tomspencerlondon.moviebooker.admin.hexagon.domain;

public class File {
    private Long id;
    private String fileName;
    private String fileUrl;
    private boolean isUploadSuccessFull;

    public File(Long id, String fileName, String fileUrl, boolean isUploadSuccessFull) {
        this.id = id;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.isUploadSuccessFull = isUploadSuccessFull;
    }

    public Long getId() {
        return id;
    }

    public String fileName() {
        return fileName;
    }

    public String fileUrl() {
        return fileUrl;
    }

    public boolean isUploadSuccessFull() {
        return isUploadSuccessFull;
    }
}
