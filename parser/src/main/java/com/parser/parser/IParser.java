package com.parser.parser;


import com.parser.exceptions.ParserException;
import org.w3c.dom.Node;

/**
 * Created by retor on 07.05.2015.
 */
public interface IParser<T> {
    T getItem(Node response) throws ParserException;
}
