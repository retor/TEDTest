package com.parserlib.beans;

import com.parserlib.exceptions.ParserException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Created by retor on 07.05.2015.
 */
public class Enclosure implements IFiller{
    private String url;
    private long length;
    private String type;

    public Enclosure(long length, String type, String url) {
        this.length = length;
        this.type = type;
        this.url = url;
    }

    public Enclosure(Node item) throws ParserException {
        fill(item);
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
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

    @Override
    public void fill(Node item) throws ParserException {
        try {
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                Element root = (Element) item;
                this.url = root.getAttribute("url");
                this.length = Long.valueOf(root.getAttribute("length"));
                this.type = root.getAttribute("type");
            }
        } catch (NullPointerException e) {
            throw new ParserException("Enclosure", e);
        }
    }
}
