package com.parser.beans;

import com.parser.exceptions.ParserException;
import org.w3c.dom.Node;

import java.io.Serializable;

/**
 * Created by retor on 12.05.2015.
 */
public interface IFiller extends Serializable {
    void fill(Node item) throws ParserException;
}
