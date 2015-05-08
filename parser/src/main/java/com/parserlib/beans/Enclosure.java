package com.parserlib.beans;

import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by retor on 07.05.2015.
 */
public class Enclosure {
    private String url;
    private long lenght;
    private String type;

    public Enclosure() {
    }

    public Enclosure(long lenght, String type, String url) {
        this.lenght = lenght;
        this.type = type;
        this.url = url;
    }

    public Enclosure(Node item) {
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
                        if (cl.item(i).getNodeName().equalsIgnoreCase("lenght"))
                            this.lenght = Long.valueOf(cl.item(i).getChildNodes().item(0).getNodeValue());
                        if (cl.item(i).getNodeName().equalsIgnoreCase("type"))
                            this.type = cl.item(i).getChildNodes().item(0).getNodeValue();
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

    public long getLenght() {
        return lenght;
    }

    public void setLenght(long lenght) {
        this.lenght = lenght;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
