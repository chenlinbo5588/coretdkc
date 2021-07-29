package com.clb.dto;

public class FileData {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    private String name;
    private String size;
    private String folderVal;

    public String getFolderVal() {
        return folderVal;
    }

    public void setFolderVal(String folderVal) {
        this.folderVal = folderVal;
    }
}
