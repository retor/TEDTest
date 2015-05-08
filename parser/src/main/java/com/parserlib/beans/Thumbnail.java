package com.parserlib.beans;


import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by retor on 07.05.2015.
 */
public class Thumbnail {
    private String url;
    private int width;
    private int height;

    public Thumbnail() {

    }

    public Thumbnail(int height, String url, int width) {
        this.height = height;
        this.url = url;
        this.width = width;
    }

    public Thumbnail(Node item) {
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
                        if (cl.item(i).getNodeName().equalsIgnoreCase("width"))
                            this.width = Integer.valueOf(cl.item(i).getChildNodes().item(0).getNodeValue());
                        if (cl.item(i).getNodeName().equalsIgnoreCase("height"))
                            this.height = Integer.valueOf(cl.item(i).getChildNodes().item(0).getNodeValue());
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
