package com.parser.beans;

import java.io.Serializable;

/**
 * Created by retor on 07.05.2015.
 */
public class Enclosure implements Serializable {
    private String url;
    private long length;
    private String type;

    public Enclosure(long length, String type, String url) {
        this.length = length;
        this.type = type;
        this.url = url;
    }

    public Enclosure() {

    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
