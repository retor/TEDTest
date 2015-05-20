package com.parser.beans;

import java.io.Serializable;

/**
 * Created by retor on 07.05.2015.
 */
public class ChannelImage implements Serializable {
    private String url;
    private String title;
    private String link;

    public ChannelImage(String link, String title, String url) {
        this.link = link;
        this.title = title;
        this.url = url;
    }

    public ChannelImage() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
