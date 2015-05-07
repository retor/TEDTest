package com.parserlib.beans;


import java.net.URL;

/**
 * Created by retor on 07.05.2015.
 */
public class Thumbnail {
    private URL url;
    private int width;
    private int height;

    public Thumbnail() {

    }

    public Thumbnail(int height, URL url, int width) {
        this.height = height;
        this.url = url;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
