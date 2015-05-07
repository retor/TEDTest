package com.parserlib.beans;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by retor on 07.05.2015.
 */
public class Item {
    private String title;
    private URL link;
    private String pubDate;
    private String description;
    private URL imageiTunes;
    private String durationiTunes;
    private Enclosure enclosure;
    private ArrayList<Media> content;
    private Thumbnail thumbnail;

    public Item() {
    }

    public Item(ArrayList<Media> content, String description, String durationiTunes, Enclosure enclosure, URL imageiTunes, URL link, String pubDate, Thumbnail thumbnail, String title) {
        this.content = content;
        this.description = description;
        this.durationiTunes = durationiTunes;
        this.enclosure = enclosure;
        this.imageiTunes = imageiTunes;
        this.link = link;
        this.pubDate = pubDate;
        this.thumbnail = thumbnail;
        this.title = title;
    }

    public ArrayList<Media> getContent() {
        return content;
    }

    public void setContent(ArrayList<Media> content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDurationiTunes() {
        return durationiTunes;
    }

    public void setDurationiTunes(String durationiTunes) {
        this.durationiTunes = durationiTunes;
    }

    public Enclosure getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;
    }

    public URL getImageiTunes() {
        return imageiTunes;
    }

    public void setImageiTunes(URL imageiTunes) {
        this.imageiTunes = imageiTunes;
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

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
