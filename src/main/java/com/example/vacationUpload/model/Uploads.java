package com.example.vacationUpload.model;

import java.util.List;

public class Uploads {
    private List<Upload> uploads;
    private long ts;

    public Uploads(List<Upload> uploads){
        this.uploads = uploads;
        this.ts = System.currentTimeMillis();
    }

    public List<Upload> getUploads(){
        return this.uploads;
    }
    public long getTs(){
        return this.ts;
    }
}
