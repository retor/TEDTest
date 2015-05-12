package com.parserlib.loader;

import com.parserlib.exceptions.LoaderException;

/**
 * Created by retor on 07.05.2015.
 */
public interface ILoader<T> {
    T getResponse(String url) throws LoaderException;
}
