package com.parserlib.beans;

import com.parserlib.exceptions.ParserException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by retor on 07.05.2015.
 */
public class ChannelImage implements IFiller {
    private String url;
    private String title;
    private String link;

    public ChannelImage(String link, String title, String url) {
        this.link = link;
        this.title = title;
        this.url = url;
    }

    public ChannelImage(Node item) throws ParserException {
        fill(item);
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
                        if (tmp.getNodeName().equalsIgnoreCase("url"))
                            this.url = tmp.getChildNodes().item(0).getNodeValue();
                        if (tmp.getNodeName().equalsIgnoreCase("title"))
                            this.title = tmp.getChildNodes().item(0).getNodeValue();
                        if (tmp.getNodeName().equalsIgnoreCase("link"))
                            this.link = tmp.getChildNodes().item(0).getNodeValue();
                    }
                }
            }
        } catch (NullPointerException e) {
            throw new ParserException("ChannelImage", e);
        }
    }
}
