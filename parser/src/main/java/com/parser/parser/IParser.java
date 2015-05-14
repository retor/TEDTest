package com.parser.parser;


import com.parser.beans.Channel;
import com.parser.exceptions.ParserException;
import org.w3c.dom.Node;

/**
 * Created by retor on 07.05.2015.
 */
public interface IParser {
    Channel getChanel(Node response) throws ParserException;
}
