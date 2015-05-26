package com.parser.loader;

import com.parser.exceptions.LoaderException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by retor on 07.05.2015.
 */
public class TedRssLoader implements ILoader<Document> {

    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;

    public TedRssLoader() {
        this.factory = DocumentBuilderFactory.newInstance();
    }

    @Override
    public Document getResponse(String url) throws LoaderException {
        Document out;
        try {
            this.builder = factory.newDocumentBuilder();
            out = builder.parse(url);
        } catch (ParserConfigurationException e) {
            throw new LoaderException("Loaded response ParserConfigurationException", e);
        } catch (SAXException e) {
            throw new LoaderException("Loaded response SAXException", e);
        } catch (IOException e) {
            throw new LoaderException("Loaded response IOException", e);
        } finally {
            builder.reset();
        }
        if (out != null) {
            return out;
        } else {
            throw new LoaderException("Loaded response is NULL");
        }
    }
}
