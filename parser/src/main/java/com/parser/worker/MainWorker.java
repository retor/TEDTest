package com.parser.worker;

import com.parser.beans.Channel;
import com.parser.exceptions.LoaderException;
import com.parser.exceptions.ParserException;
import com.parser.loader.ILoader;
import com.parser.parser.IParser;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Created by retor on 07.05.2015.
 */
public class MainWorker implements IWorker<Channel, Document> {
    private ILoader<Document> loader;
    private IParser<Channel> parser;

    public MainWorker(ILoader<Document> loader, IParser<Channel> parser) {
        this.loader = loader;
        this.parser = parser;
    }

    @Override
    public ArrayList<Channel> getData(String... url) throws LoaderException, ParserException {
        ArrayList<Channel> out = new ArrayList<>();
        if (url.length > 1) {
            Channel tmp;
            for (int i = 0; i < url.length; i++) {
                tmp = getSingleData(url[i]);
                tmp.setRssUrl(url[i]);
                out.add(tmp);
            }
        } else {
            Document doc = loader.getResponse(url[0]);
            NodeList channels = doc.getElementsByTagName("channel");
            for (int i = 0; i < channels.getLength(); i++) {
                Channel tmp = parser.getItem(channels.item(i));
                tmp.setRssUrl(url[0]);
                out.add(tmp);
            }
        }
        return out;
    }

    @Override
    public Channel getSingleData(String url) throws LoaderException, ParserException {
        Document doc = loader.getResponse(url);
        NodeList channels = doc.getElementsByTagName("channel");
        Channel out = parser.getItem(channels.item(0));
        out.setRssUrl(url);
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
