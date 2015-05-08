package com.parserlib.beans;

import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Created by retor on 07.05.2015.
 */
public class Item {
    private String title;
    private String link;
    private String pubDate;
    private String description;
    private String imageiTunes;
    private String durationiTunes;
    private Enclosure enclosure;
    private ArrayList<Media> content;
    private Thumbnail thumbnail;

    public Item() {
    }

    public Item(ArrayList<Media> content, String description, String durationiTunes, Enclosure enclosure, String imageiTunes, String link, String pubDate, Thumbnail thumbnail, String title) {
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

    public Item(Node item) {
        try {
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                Element root = (Element) item;
                int l = root.getChildNodes().getLength();
                NodeList cl = item.getChildNodes();
                for (int i = 0; i < l; i++) {
                    String rr = cl.item(i).getLocalName() + cl.item(i).getNodeType() + cl.item(i).getNodeName();
                    rr.toString();
                    if (cl.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        if (cl.item(i).getNodeName().equalsIgnoreCase("title"))
                            this.title = cl.item(i).getChildNodes().item(0).getNodeValue();
                        if (cl.item(i).getNodeName().equalsIgnoreCase("description"))
                            this.description = cl.item(i).getChildNodes().item(0).getNodeValue();
                        if (cl.item(i).getNodeName().equalsIgnoreCase("pubDate"))
                            this.pubDate = cl.item(i).getChildNodes().item(0).getNodeValue();
                        if (cl.item(i).getNodeName().equalsIgnoreCase("imageiTunes"))
                            this.imageiTunes = cl.item(i).getChildNodes().item(0).getNodeValue();
                        if (cl.item(i).getNodeName().equalsIgnoreCase("durationiTunes"))
                            this.durationiTunes = cl.item(i).getChildNodes().item(0).getNodeValue();
                        if (cl.item(i).getNodeName().equalsIgnoreCase("link"))
                            this.link = cl.item(i).getChildNodes().item(0).getNodeValue();
                        if (cl.item(i).getNodeName().equalsIgnoreCase("enclosure"))
                            this.enclosure = new Enclosure(cl.item(i));
                        if (cl.item(i).getNodeName().equalsIgnoreCase("thumbnail"))
                            this.thumbnail = new Thumbnail(cl.item(i));
                        if (cl.item(i).getNodeName().equalsIgnoreCase("media"))
                            this.content.add(new Media(cl.item(i)));
                    }
                    if (cl.item(i).getNodeType() == Node.ENTITY_NODE) {
                        Entity tmp = (Entity) cl.item(i);
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
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

    public String getImageiTunes() {
        return imageiTunes;
    }

    public void setImageiTunes(String imageiTunes) {
        this.imageiTunes = imageiTunes;
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
