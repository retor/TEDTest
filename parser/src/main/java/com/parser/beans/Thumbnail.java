package com.parser.beans;


import com.parser.exceptions.ParserException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Created by retor on 07.05.2015.
 */
public class Thumbnail implements IFiller{
    private String url;
    private int width;
    private int height;

    public Thumbnail(int height, String url, int width) {
        this.height = height;
        this.url = url;
        this.width = width;
    }

    public Thumbnail(Node item) throws ParserException {
        fill(item);
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

    @Override
    public void fill(Node item) throws ParserException {
        try {
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                NamedNodeMap attributes = item.getAttributes();
                this.url = attributes.getNamedItem("url").getNodeValue();
                this.width = Integer.valueOf(attributes.getNamedItem("width").getNodeValue());
                this.height = Integer.valueOf(attributes.getNamedItem("height").getNodeValue());
            }
        } catch (NullPointerException e) {
            throw new ParserException("Thumb null", e);
        }
    }
}
