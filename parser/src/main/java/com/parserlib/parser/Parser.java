package com.parserlib.parser;

import com.parserlib.beans.Channel;
import com.parserlib.beans.Item;
import com.parserlib.beans.Media;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by retor on 07.05.2015.
 */
public class Parser implements IParser {

    DocumentBuilderFactory factory;
    DocumentBuilder builder;

    public Parser() throws ParserConfigurationException {
        this.factory = DocumentBuilderFactory.newInstance();
        this.builder = factory.newDocumentBuilder();
    }

    @Override
    public Channel getChanel(String response) {

        try {
            Document document = builder.parse(response);
            document.getDocumentElement().getChildNodes();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Item getItems(Channel channel) {
        return null;
    }

    @Override
    public ArrayList<Media> getMedia(Item item) {
        return null;
    }
}
