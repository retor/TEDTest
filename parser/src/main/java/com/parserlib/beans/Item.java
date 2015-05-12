package com.parserlib.beans;

import com.parserlib.exceptions.ParserException;
import org.w3c.dom.*;

import java.util.ArrayList;

/**
 * Created by retor on 07.05.2015.
 */
public class Item implements IFiller{
    private String title;
    private String link;
    private String pubDate;
    private String description;
    private String imageTunes;
    private String durationiTunes;
    private Enclosure enclosure;
    private ArrayList<Media> content = new ArrayList<Media>();
    private Thumbnail thumbnail;

    public Item(ArrayList<Media> content, String description, String durationiTunes, Enclosure enclosure, String imageTunes, String link, String pubDate, Thumbnail thumbnail, String title) {
        this.content = content;
        this.description = description;
        this.durationiTunes = durationiTunes;
        this.enclosure = enclosure;
        this.imageTunes = imageTunes;
        this.link = link;
        this.pubDate = pubDate;
        this.thumbnail = thumbnail;
        this.title = title;
    }

    public Item(Node item) throws ParserException {
        fill(item);
    }

    public ArrayList<Media> getContent() {
        return content;
    }

    public void setContent(ArrayList<Media> content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDurationiTunes() {
        return durationiTunes;
    }

    public void setDurationiTunes(String durationiTunes) {
        this.durationiTunes = durationiTunes;
    }

    public Enclosure getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;
    }

    public String getImageTunes() {
        return imageTunes;
    }

    public void setImageTunes(String imageiTunes) {
        this.imageTunes = imageiTunes;
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

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
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
                            this.title = tmp.getChildNodes().item(0).getNodeValue();
                        if (tmp.getNodeName().equalsIgnoreCase("description"))
                            this.description = tmp.getChildNodes().item(0).getNodeValue();
                        if (tmp.getNodeName().equalsIgnoreCase("pubDate"))
                            this.pubDate = tmp.getChildNodes().item(0).getNodeValue();
                        if (tmp.getNodeName().equalsIgnoreCase("itunes:image"))
                            this.imageTunes = tmp.getAttributes().getNamedItem("url").getNodeValue();
                        if (tmp.getNodeName().equalsIgnoreCase("itunes:duration"))
                            this.durationiTunes = tmp.getChildNodes().item(0).getNodeValue();
                        if (tmp.getNodeName().equalsIgnoreCase("link"))
                            this.link = tmp.getChildNodes().item(0).getNodeValue();
                        if (tmp.getNodeName().equalsIgnoreCase("enclosure"))
                            this.enclosure = new Enclosure(tmp);
                        if (tmp.getNodeName().equalsIgnoreCase("media:group")) {
                            int al = tmp.getChildNodes().getLength();
                            Element tmpMedia = (Element) tmp;
                            NodeList childs = tmpMedia.getChildNodes();
                            for (int a = 0; a < al; a++) {
                                Node tmpM = childs.item(a);
                                if (tmpM.getNodeType()==Node.ELEMENT_NODE){
                                    if (tmpM.getNodeName()!= null && tmpM.getNodeName().equalsIgnoreCase("media:content")) {
                                        tmpM.getLocalName();
                                        this.content.add(new Media(tmpM));
                                    }
                                    if (tmpM.getNodeName() != null && tmpM.getNodeName().equalsIgnoreCase("media:thumbnail")) {
                                        this.thumbnail = new Thumbnail(tmpM);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            throw new ParserException("Item", e);
        }
    }
}
