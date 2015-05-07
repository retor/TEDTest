package com.parserlib.loader;

import com.parserlib.exceptions.LoaderException;
import org.w3c.dom.Document;

/**
 * Created by retor on 07.05.2015.
 */
public interface ILoader {
    Document getResponse(String url) throws LoaderException;
}
