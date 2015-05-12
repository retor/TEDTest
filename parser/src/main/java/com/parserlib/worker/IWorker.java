package com.parserlib.worker;

import com.parserlib.exceptions.LoaderException;
import com.parserlib.exceptions.ParserException;
import com.parserlib.loader.ILoader;
import com.parserlib.parser.IParser;

/**
 * Created by retor on 07.05.2015.
 */
public interface IWorker<T> {
    T getData(String url) throws LoaderException, ParserException;
    void setLoader(ILoader loader);
    void setParser(IParser parser);
}
