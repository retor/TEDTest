package com.parser.parser;

import com.parser.beans.*;
import com.parser.exceptions.ParserException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by retor on 07.05.2015.
 */
public class Parser implements IParser<Channel> {

    private IParser<ChannelImage> channelImageFiller = new IParser<ChannelImage>() {
        @Override
        public ChannelImage getItem(Node item) throws ParserException {
            try {
                ChannelImage bean = new ChannelImage();
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    Element root = (Element) item;
                    NodeList cl = root.getChildNodes();
                    int l = cl.getLength();
                    for (int i = 0; i < l; i++) {
                        Node tmp = cl.item(i);
                        if (tmp.getNodeType() == Node.ELEMENT_NODE) {
                            if (tmp.getNodeName().equalsIgnoreCase("url"))
                                bean.setUrl(tmp.getChildNodes().item(0).getNodeValue());
                            if (tmp.getNodeName().equalsIgnoreCase("title"))
                                bean.setTitle(tmp.getChildNodes().item(0).getNodeValue());
                            if (tmp.getNodeName().equalsIgnoreCase("link"))
                                bean.setLink(tmp.getChildNodes().item(0).getNodeValue());
                        }
                    }
                }
                return null;
            } catch (NullPointerException e) {
                throw new ParserException("ChannelImage", e);
            }
        }
    };
    private IParser<Enclosure> enclosureFiller = new IParser<Enclosure>() {
        @Override
        public Enclosure getItem(Node item) throws ParserException {
            try {
                Enclosure bean = new Enclosure();
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    Element root = (Element) item;
                    bean.setUrl(root.getAttribute("url"));
                    bean.setLength(Long.parseLong(root.getAttribute("length")));
                    bean.setType(root.getAttribute("type"));
                }
                return bean;
            } catch (NullPointerException e) {
                throw new ParserException("Enclosure", e);
            }
        }
    };
    private IParser<Media> mediaFiller = new IParser<Media>() {
        @Override
        public Media getItem(Node item) throws ParserException {
            try {
                Media bean = new Media();
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    NamedNodeMap attributes = item.getAttributes();
                    bean.setUrl(attributes.getNamedItem("url").getNodeValue());
                    bean.setBitRate(Integer.valueOf(attributes.getNamedItem("bitrate").getNodeValue()));
                    bean.setFileSize(Integer.valueOf(attributes.getNamedItem("fileSize").getNodeValue()));
                    bean.setDuration(Long.valueOf(attributes.getNamedItem("duration").getNodeValue()));
                }
                return bean;
            } catch (NullPointerException e) {
                throw new ParserException("Media", e);
            }
        }
    };
    private IParser<Thumbnail> thumbnailFiller = new IParser<Thumbnail>() {
        @Override
        public Thumbnail getItem(Node item) throws ParserException {
            try {
                Thumbnail bean = new Thumbnail();
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    NamedNodeMap attributes = item.getAttributes();
                    bean.setUrl(attributes.getNamedItem("url").getNodeValue());
                    bean.setWidth(Integer.valueOf(attributes.getNamedItem("width").getNodeValue()));
                    bean.setHeight(Integer.valueOf(attributes.getNamedItem("height").getNodeValue()));
                }
                return bean;
            } catch (NullPointerException e) {
                throw new ParserException("Thumb null", e);
            }
        }
    };
    private IParser<Item> itemFiller = new IParser<Item>() {
        @Override
        public Item getItem(Node item) throws ParserException {
            try {
                Item bean = new Item();
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    Element root = (Element) item;
                    NodeList cl = root.getChildNodes();
                    int l = cl.getLength();
                    for (int i = 0; i < l; i++) {
                        Node tmp = cl.item(i);
                        if (tmp.getNodeType() == Node.ELEMENT_NODE) {
                            if (tmp.getNodeName().equalsIgnoreCase("title"))
                                bean.setTitle(tmp.getChildNodes().item(0).getNodeValue());
                            if (tmp.getNodeName().equalsIgnoreCase("description"))
                                bean.setDescription(tmp.getChildNodes().item(0).getNodeValue());
                            if (tmp.getNodeName().equalsIgnoreCase("pubDate"))
                                bean.setPubDate(tmp.getChildNodes().item(0).getNodeValue());
                            if (tmp.getNodeName().equalsIgnoreCase("itunes:image"))
                                bean.setImageTunes(tmp.getAttributes().getNamedItem("url").getNodeValue());
                            if (tmp.getNodeName().equalsIgnoreCase("itunes:duration"))
                                bean.setDurationiTunes(tmp.getChildNodes().item(0).getNodeValue());
                            if (tmp.getNodeName().equalsIgnoreCase("link"))
                                bean.setLink(tmp.getChildNodes().item(0).getNodeValue());
                            if (tmp.getNodeName().equalsIgnoreCase("enclosure"))
                                bean.setEnclosure(enclosureFiller.getItem(tmp));
                            if (tmp.getNodeName().equalsIgnoreCase("media:group")) {
                                int al = tmp.getChildNodes().getLength();
                                Element tmpMedia = (Element) tmp;
                                NodeList childs = tmpMedia.getChildNodes();
                                for (int a = 0; a < al; a++) {
                                    Node tmpM = childs.item(a);
                                    if (tmpM.getNodeType() == Node.ELEMENT_NODE) {
                                        if (tmpM.getNodeName() != null && tmpM.getNodeName().equalsIgnoreCase("media:content")) {
//                                            tmpM.getLocalName();
                                            bean.addContentItem(mediaFiller.getItem(tmpM));
                                        }
                                        if (tmpM.getNodeName() != null && tmpM.getNodeName().equalsIgnoreCase("media:thumbnail")) {
                                            bean.setThumbnail(thumbnailFiller.getItem(tmpM));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return bean;
            } catch (NullPointerException e) {
                throw new ParserException("Item", e);
            }
        }
    };
    private IParser<Channel> channelFiller = new IParser<Channel>() {
        @Override
        public Channel getItem(Node item) throws ParserException {
            try {
                Channel bean = new Channel();
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    Element root = (Element) item;
                    NodeList cl = root.getChildNodes();
                    int l = cl.getLength();
                    for (int i = 0; i < l; i++) {
                        Node tmp = cl.item(i);
                        if (tmp.getNodeType() == Node.ELEMENT_NODE) {
                            if (tmp.getNodeName().equalsIgnoreCase("title"))
                                bean.setTitle(tmp.getChildNodes().item(0).getNodeValue());
                            if (tmp.getNodeName().equalsIgnoreCase("description"))
                                bean.setDescription(tmp.getChildNodes().item(0).getNodeValue());
                            if (tmp.getNodeName().equalsIgnoreCase("language"))
                                bean.setLanguage(tmp.getChildNodes().item(0).getNodeValue());
                            if (tmp.getNodeName().equalsIgnoreCase("pubDate"))
                                bean.setPubDate(tmp.getChildNodes().item(0).getNodeValue());
                            if (tmp.getNodeName().equalsIgnoreCase("image"))
                                bean.setImage(channelImageFiller.getItem(tmp));
                            if (tmp.getNodeName().equalsIgnoreCase("item"))
                                bean.addItem(itemFiller.getItem(tmp));
                            if (tmp.getNodeName().equalsIgnoreCase("link"))
                                bean.setLink(tmp.getChildNodes().item(0).getNodeValue());
                        }
                    }
                }
                return bean;
            } catch (NullPointerException e) {
                throw new ParserException("Channel", e);
            }
        }
    };

    @Override
    public Channel getItem(Node response) throws ParserException {
        return channelFiller.getItem(response);
    }

}
