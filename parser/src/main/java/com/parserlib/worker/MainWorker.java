package com.parserlib.worker;

import com.parserlib.beans.Channel;
import com.parserlib.exceptions.LoaderException;
import com.parserlib.loader.ILoader;
import com.parserlib.parser.IParser;

import java.util.ArrayList;

/**
 * Created by retor on 07.05.2015.
 */
public class MainWorker implements IWorker {
    ILoader loader;
    IParser parser;

    public MainWorker(ILoader loader, IParser parser) {
        this.loader = loader;
        this.parser = parser;
    }

    @Override
    public ArrayList<Channel> getData(String url) throws LoaderException {
        ArrayList<Channel> out = new ArrayList<Channel>();
        out.add(parser.getChanel(loader.getResponse(url)));
        return out;
    }

    public ILoader getLoader() {
        return loader;
    }

    public void setLoader(ILoader loader) {
        this.loader = loader;
    }

    public IParser getParser() {
        return parser;
    }

    public void setParser(IParser parser) {
        this.parser = parser;
    }
}
