package com.example.vacationUpload;

import org.apache.commons.fileupload.ProgressListener;

public class UploadTracker implements ProgressListener {
    private long pUnixStartTime = 0L;
    private long pUnixEndTime = 0L;
    private long pBytesRead = 0L;
    private long pContentLength = 0L;
    private String pFileName;

    public UploadTracker(String fileName, long startTime)
    {
        this.pFileName = fileName;
        this.pUnixStartTime = startTime;
    }

    public long getpBytesRead() {
        return pBytesRead;
    }

    public void setpBytesRead(long pBytesRead) {
        this.pBytesRead = pBytesRead;
    }

    public long getpContentLength() {
        return pContentLength;
    }

    public void setpContentLength(long pContentLength) {
        this.pContentLength = pContentLength;
    }

    public long getUploadDuration() {
        return pUnixEndTime - pUnixStartTime;
    }

    public String getpFileName() {
        return pFileName;
    }
    public String getId() {
        return String.format("%s-<%s>", pFileName, pUnixStartTime);
    }

    public boolean isFinished(){
        return pUnixEndTime > pUnixStartTime;
    }

    public boolean inProgress(){
        return !(pUnixEndTime > pUnixStartTime) && (pBytesRead < pContentLength);
    }

    public void setUnixEndTime(long endTime){
        this.pUnixEndTime = endTime;
    }

    @Override
    public void update(long pBytesRead, long pContentLength, int pItems) {
        //System.out.println("Bytes read " + pBytesRead + " len " + pContentLength + " item " + pItems);
        this.pBytesRead = pBytesRead;
        this.pContentLength = pContentLength;
        if(!(pBytesRead < pContentLength)){
            pUnixEndTime = System.currentTimeMillis();
        }
    }

}
