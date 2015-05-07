package com.parserlib.beans;

import java.net.URL;

/**
 * Created by retor on 07.05.2015.
 */
public class Media {
    private URL url;
    private long duration;
    private int fileSize;
    private int bitRate;

    public Media() {
    }

    public Media(int bitRate, long duration, int fileSize, URL url) {
        this.bitRate = bitRate;
        this.duration = duration;
        this.fileSize = fileSize;
        this.url = url;
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

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
