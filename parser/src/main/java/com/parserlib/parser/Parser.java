package com.parserlib.parser;

import com.parserlib.beans.Channel;
import com.parserlib.exceptions.ParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by retor on 07.05.2015.
 */
public class Parser implements IParser {

    public Parser() {
    }

    @Override
    public Channel getChanel(Document response) throws ParserException {
        NodeList channels = response.getElementsByTagName("channel");
        response.getElementsByTagName("item");
        int nlistlenght = channels.getLength();
        Node channel = channels.item(0);
        Channel ch = new Channel(response.getDocumentElement().getElementsByTagName("channel").item(0));
        return ch;
    }

}
