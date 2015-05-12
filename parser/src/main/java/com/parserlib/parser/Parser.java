package com.parserlib.parser;

import com.parserlib.beans.Channel;
import com.parserlib.exceptions.ParserException;
import org.w3c.dom.Node;

/**
 * Created by retor on 07.05.2015.
 */
public class Parser implements IParser {

    @Override
    public Channel getChanel(Node response) throws ParserException {
        return new Channel(response);
    }
}
