package com.parser.beans;

import com.parser.exceptions.ParserException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Created by retor on 07.05.2015.
 */
public class Channel implements IFiller{
    private String title;
    private String link;
    private String description;
    private String language;
    private String pubDate;
    private ChannelImage image;
    private ArrayList<Item> items = new ArrayList<Item>();

    public Channel(Node in) throws ParserException{
        fill(in);
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

    public void addItems(ArrayList<Item> items) {
        this.items.addAll(items);
    }

    public void addItem(Item item) {
        this.items.add(item);
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

    @Override
    public void fill(Node item) throws ParserException {
        try {
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                Element root = (Element) item;
                NodeList cl = root.getChildNodes();
                int l = cl.getLength();
                for (int i = 0; i < l; i++) {
                    Node tmp = cl.item(i);
                    if (tmp.getNodeType() == Node.ELEMENT_NODE) {
                        if (tmp.getNodeName().equalsIgnoreCase("title"))
                            setTitle(tmp.getChildNodes().item(0).getNodeValue());
                        if (tmp.getNodeName().equalsIgnoreCase("description"))
                            setDescription(tmp.getChildNodes().item(0).getNodeValue());
                        if (tmp.getNodeName().equalsIgnoreCase("language"))
                            setLanguage(tmp.getChildNodes().item(0).getNodeValue());
                        if (tmp.getNodeName().equalsIgnoreCase("pubDate"))
                            setPubDate(tmp.getChildNodes().item(0).getNodeValue());
                        if (tmp.getNodeName().equalsIgnoreCase("image"))
                            setImage(new ChannelImage(tmp));
                        if (tmp.getNodeName().equalsIgnoreCase("item"))
                            addItem(new Item(tmp));
                        if (tmp.getNodeName().equalsIgnoreCase("link"))
                            setLink(tmp.getChildNodes().item(0).getNodeValue());
                    }
                }
            }
        } catch (NullPointerException e) {
            throw new ParserException("Channel", e);
        }
    }
}
