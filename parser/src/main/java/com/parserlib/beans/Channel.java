package com.parserlib.beans;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by retor on 07.05.2015.
 */
public class Channel {
    private String title;
    private URL link;
    private String description;
    private String language;
    private String pubDate;
    private ChannelImage image;
    private ArrayList<Item> items;

    public Channel() {
    }

    public Channel(String description, ChannelImage image, ArrayList<Item> items, String language, URL link, String pubDate, String title) {
        this.description = description;
        this.image = image;
        this.items = items;
        this.language = language;
        this.link = link;
        this.pubDate = pubDate;
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ChannelImage getImage() {
        return image;
    }

    public void setImage(ChannelImage image) {
        this.image = image;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
