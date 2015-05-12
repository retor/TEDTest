package com.parserlib.beans;

import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Created by retor on 07.05.2015.
 */
public class Channel {
    private String title;
    private String link;
    private String description;
    private String language;
    private String pubDate;
    private ChannelImage image;
    private ArrayList<Item> items;

    public Channel(Node in) {
        try {
            if (in.getNodeType() == Node.ELEMENT_NODE) {
                Element root = (Element) in;
                int l = root.getChildNodes().getLength();
                NodeList cl = in.getChildNodes();
                for (int i = 0; i < l; i++) {
                    String rr = cl.item(i).getLocalName() + cl.item(i).getNodeType() + cl.item(i).getNodeName();
                    rr.toString();
                    if (cl.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        if (cl.item(i).getNodeName().equalsIgnoreCase("title"))
                            this.title = cl.item(i).getChildNodes().item(0).getNodeValue();
                        if (cl.item(i).getNodeName().equalsIgnoreCase("description"))
                            this.description = cl.item(i).getChildNodes().item(0).getNodeValue();
                        if (cl.item(i).getNodeName().equalsIgnoreCase("language"))
                            this.language = cl.item(i).getChildNodes().item(0).getNodeValue();
                        if (cl.item(i).getNodeName().equalsIgnoreCase("pubDate"))
                            this.pubDate = cl.item(i).getChildNodes().item(0).getNodeValue();
                        if (cl.item(i).getNodeName().equalsIgnoreCase("image"))
                            this.image = new ChannelImage(cl.item(i));
                        if (cl.item(i).getNodeName().equalsIgnoreCase("item"))
                            this.items.add(new Item(cl.item(i)));
                        if (cl.item(i).getNodeName().equalsIgnoreCase("link"))
                            this.link = cl.item(i).getChildNodes().item(0).getNodeValue();
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

    public Channel(String description, ChannelImage image, ArrayList<Item> items, String language, String link, String pubDate, String title) {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
