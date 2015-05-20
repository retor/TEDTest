package com.parser.beans;

import java.io.Serializable;

/**
 * Created by retor on 07.05.2015.
 */
public class Media implements Serializable {
    private String url;
    private long duration;
    private int fileSize;
    private int bitRate;

    public Media(int bitRate, long duration, int fileSize, String url) {
        this.bitRate = bitRate;
        this.duration = duration;
        this.fileSize = fileSize;
        this.url = url;
    }

    public Media() {

    }

    public int getBitRate() {
        return bitRate;
    }

    public void setBitRate(int bitRate) {
        this.bitRate = bitRate;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
