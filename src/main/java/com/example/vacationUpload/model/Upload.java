package com.example.vacationUpload.model;

public class Upload {
    String id;
    long size;
    long uploaded;

    public Upload(String id, long size, long uploaded){
        this.id = id;
        this.size = size;
        this.uploaded = uploaded;
    }

    public String getId(){
        return this.id;
    }

    public long getSize(){
        return this.size;
    }

    public long getUploaded(){
        return this.uploaded;
    }
}
