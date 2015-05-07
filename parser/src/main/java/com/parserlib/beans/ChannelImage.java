package com.parserlib.beans;

import java.net.URL;

/**
 * Created by retor on 07.05.2015.
 */
public class ChannelImage {
    private URL url;
    private String title;
    private URL link;

    public ChannelImage() {
    }

    public ChannelImage(URL link, String title, URL url) {
        this.link = link;
        this.title = title;
        this.url = url;
    }

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
