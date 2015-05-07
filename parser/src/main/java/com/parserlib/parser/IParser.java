package com.parserlib.parser;


import com.parserlib.beans.Channel;
import com.parserlib.beans.Item;
import com.parserlib.beans.Media;

import java.util.ArrayList;

/**
 * Created by retor on 07.05.2015.
 */
public interface IParser {
    Channel getChanel(String response);
    Item getItems(Channel channel);
    ArrayList<Media> getMedia(Item item);
}
