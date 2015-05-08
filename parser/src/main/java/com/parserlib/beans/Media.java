package com.parserlib.beans;

import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by retor on 07.05.2015.
 */
public class Media {
    private String url;
    private long duration;
    private int fileSize;
    private int bitRate;

    public Media() {
    }

    public Media(int bitRate, long duration, int fileSize, String url) {
        this.bitRate = bitRate;
        this.duration = duration;
        this.fileSize = fileSize;
        this.url = url;
    }

    public Media(Node item) {
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
                        if (cl.item(i).getNodeName().equalsIgnoreCase("duration"))
                            this.duration = Long.valueOf(cl.item(i).getChildNodes().item(0).getNodeValue());
                        if (cl.item(i).getNodeName().equalsIgnoreCase("fileSize"))
                            this.fileSize = Integer.valueOf(cl.item(i).getChildNodes().item(0).getNodeValue());
                        if (cl.item(i).getNodeName().equalsIgnoreCase("bitRate"))
                            this.bitRate = Integer.valueOf(cl.item(i).getChildNodes().item(0).getNodeValue());
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

    public int getBitRate() {
        return bitRate;
    }

    public void setBitRate(int bitRate) {
        this.bitRate = bitRate;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
