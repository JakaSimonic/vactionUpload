package com.example.vacationUpload.model;

public class Duration {
    String id;
    long upload_duration;

    public Duration(String id, long uploadDuration){
        this.id = id;
        this.upload_duration = uploadDuration;
    }

    public String getId(){
        return this.id;
    }

    public long getUpload_duration(){
        return this.upload_duration;
    }
}
