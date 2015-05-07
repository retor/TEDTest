package com.parserlib.parser;


import com.parserlib.beans.Channel;
import com.parserlib.exceptions.ParserException;
import org.w3c.dom.Document;

/**
 * Created by retor on 07.05.2015.
 */
public interface IParser {
    Channel getChanel(Document response) throws ParserException;
}
