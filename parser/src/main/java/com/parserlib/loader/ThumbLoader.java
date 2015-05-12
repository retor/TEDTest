package com.parserlib.loader;

import com.parserlib.exceptions.LoaderException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Created by retor on 12.05.2015.
 */
public class ThumbLoader implements ILoader<byte[]> {
    @Override
    public byte[] getResponse(String url) throws LoaderException {
        byte[] out;
        BufferedInputStream is = null;
        try {
            is = new BufferedInputStream(new URL(url).openStream());
            out = new byte[is.available()];//TODO: Read Bitmap
            is.read(out);
        } catch (IOException e) {
            e.printStackTrace();
            throw new LoaderException("ThumbLoader", e);
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return out;
    }
}
