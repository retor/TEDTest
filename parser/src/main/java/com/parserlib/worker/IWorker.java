package com.parserlib.worker;

import com.parserlib.beans.Channel;
import com.parserlib.exceptions.LoaderException;
import com.parserlib.exceptions.ParserException;

import java.util.ArrayList;

/**
 * Created by retor on 07.05.2015.
 */
public interface IWorker {
    ArrayList<Channel> getData(String url) throws LoaderException, ParserException;
}
