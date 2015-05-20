package com.parser.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by retor on 07.05.2015.
 */
public class Item implements Serializable {
    private String title;
    private String link;
    private String pubDate;
    private String description;
    private String imageTunes;
    private String durationiTunes;
    private Enclosure enclosure;
    private ArrayList<Media> content = new ArrayList<Media>();
    private Thumbnail thumbnail;

    public Item(ArrayList<Media> content, String description, String durationiTunes, Enclosure enclosure, String imageTunes, String link, String pubDate, Thumbnail thumbnail, String title) {
        this.content = content;
        this.description = description;
        this.durationiTunes = durationiTunes;
        this.enclosure = enclosure;
        this.imageTunes = imageTunes;
        this.link = link;
        this.pubDate = pubDate;
        this.thumbnail = thumbnail;
        this.title = title;
    }

    public Item() {
    }

    public ArrayList<Media> getContent() {
        return content;
    }

    public void addContentItem(Media content) {
        this.content.add(content);
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

    public String getImageTunes() {
        return imageTunes;
    }

    public void setImageTunes(String imageiTunes) {
        this.imageTunes = imageiTunes;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
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
