package com.parserlib.beans;

import java.net.URL;

/**
 * Created by retor on 07.05.2015.
 */
public class Enclosure {
    private URL url;
    private long lenght;
    private String type;

    public Enclosure() {
    }

    public Enclosure(long lenght, String type, URL url) {
        this.lenght = lenght;
        this.type = type;
        this.url = url;
    }

    public long getLenght() {
        return lenght;
    }

    public void setLenght(long lenght) {
        this.lenght = lenght;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
