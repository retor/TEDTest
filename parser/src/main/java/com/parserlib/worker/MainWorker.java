package com.parserlib.worker;

import com.parserlib.beans.Channel;
import com.parserlib.exceptions.LoaderException;
import com.parserlib.exceptions.ParserException;
import com.parserlib.loader.ILoader;
import com.parserlib.parser.IParser;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Created by retor on 07.05.2015.
 */
public class MainWorker implements IWorker<ArrayList<Channel>> {
    private ILoader<Document> loader;
    private IParser parser;

    public MainWorker(ILoader loader, IParser parser) {
        this.loader = loader;
        this.parser = parser;
    }

    @Override
    public ArrayList<Channel> getData(String url) throws LoaderException, ParserException {
        ArrayList<Channel> out = new ArrayList<Channel>();
        Document doc = loader.getResponse(url);
        NodeList channels = doc.getElementsByTagName("channel");
        for (int i = 0; i < channels.getLength(); i++) {
            Channel tmp = parser.getChanel(channels.item(i));
            out.add(tmp);
        }
        return out;
    }

    @Override
    public void setLoader(ILoader loader) {
        this.loader = loader;
    }

    @Override
    public void setParser(IParser parser) {
        this.parser = parser;
    }
}
