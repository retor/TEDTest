package com.parserlib.loader;

import com.parserlib.exceptions.LoaderException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by retor on 07.05.2015.
 */
public class RssLoader implements ILoader {
    private int tryCounter = 0;

    DocumentBuilderFactory factory;
    DocumentBuilder builder;





    @Override
    public String getResponse(String url) throws LoaderException {
        this.factory = DocumentBuilderFactory.newInstance();
        try {
            this.builder = factory.newDocumentBuilder();
            Document document = builder.parse(url);
            document.getDocumentElement().getChildNodes();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String tmp = null;
        try {
            InputStream inputStream = new URL(url).openStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            tmp = new String(baos.toByteArray());
        } catch (IOException e) {
            throw new LoaderException("Loaded response is NULL");
        }
        if (tmp != null) {
            tryCounter=0;
            return tmp;
        }else {
            throw new LoaderException("Loaded response is NULL");
        }
    }
}
