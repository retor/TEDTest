package com.parser.worker;

import com.parser.exceptions.LoaderException;
import com.parser.exceptions.ParserException;
import com.parser.loader.ILoader;
import com.parser.parser.IParser;

/**
 * Created by retor on 07.05.2015.
 */
public interface IWorker<T> {
    T getData(String url) throws LoaderException, ParserException;
    void setLoader(ILoader loader);
    void setParser(IParser parser);
}
