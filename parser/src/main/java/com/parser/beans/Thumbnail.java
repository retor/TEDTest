package com.parser.beans;


import java.io.Serializable;

/**
 * Created by retor on 07.05.2015.
 */
public class Thumbnail implements Serializable {
    private String url;
    private int width;
    private int height;

    public Thumbnail(int height, String url, int width) {
        this.height = height;
        this.url = url;
        this.width = width;
    }

    public Thumbnail() {

    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
