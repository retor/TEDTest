package com.parser.worker;

import com.parser.exceptions.LoaderException;
import com.parser.exceptions.ParserException;
import com.parser.loader.ILoader;
import com.parser.parser.IParser;

import java.util.ArrayList;

/**
 * Created by retor on 07.05.2015.
 */
public interface IWorker<T> {
    ArrayList<T> getData(String... url) throws LoaderException, ParserException;

    T getSingleData(String url) throws LoaderException, ParserException;

    void setLoader(ILoader loader);

    void setParser(IParser<T> parser);
}
