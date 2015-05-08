package com.parserlib.beans;

import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by retor on 07.05.2015.
 */
public class ChannelImage {
    private String url;
    private String title;
    private String link;

    public ChannelImage() {
    }

    public ChannelImage(String link, String title, String url) {
        this.link = link;
        this.title = title;
        this.url = url;
    }

    public ChannelImage(Node item) {
        try {
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                Element root = (Element) item;
                int l = root.getChildNodes().getLength();
                NodeList cl = item.getChildNodes();
                for (int i = 0; i < l; i++) {
                    String rr = cl.item(i).getLocalName() + cl.item(i).getNodeType() + cl.item(i).getNodeName();
                    rr.toString();
                    if (cl.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        if (cl.item(i).getNodeName().equalsIgnoreCase("url"))
                            this.url = cl.item(i).getChildNodes().item(0).getNodeValue();
                        if (cl.item(i).getNodeName().equalsIgnoreCase("title"))
                            this.title = cl.item(i).getChildNodes().item(0).getNodeValue();
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
