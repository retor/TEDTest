package com.parser.beans;

import com.parser.exceptions.ParserException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Created by retor on 07.05.2015.
 */
public class Media implements IFiller{
    private String url;
    private long duration;
    private int fileSize;
    private int bitRate;

    public Media(int bitRate, long duration, int fileSize, String url) {
        this.bitRate = bitRate;
        this.duration = duration;
        this.fileSize = fileSize;
        this.url = url;
    }

    public Media(Node item) throws ParserException {
        fill(item);
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

    @Override
    public void fill(Node item) throws ParserException {
        try {
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                NamedNodeMap attributes = item.getAttributes();
                this.url = attributes.getNamedItem("url").getNodeValue();
                this.bitRate = Integer.valueOf(attributes.getNamedItem("bitrate").getNodeValue());
                this.fileSize = Integer.valueOf(attributes.getNamedItem("fileSize").getNodeValue());
                this.duration = Long.valueOf(attributes.getNamedItem("duration").getNodeValue());
            }
        } catch (NullPointerException e) {
            throw new ParserException("Media", e);
        }
    }
}
