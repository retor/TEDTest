package com.retor.tedtest.main;

import com.parser.beans.Channel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by retor on 19.05.2015.
 */
/*Helper class to store Channels array*/
public class Content implements Serializable {
    private ArrayList<Channel> channels;

    public Content(ArrayList<Channel> channels) {
        this.channels = channels;
    }

    public ArrayList<Channel> getChannels() {
        return channels;
    }

    public Channel getChannel(int pos) {
        if (hasChannels())
            return channels.get(pos);
        else
            return null;
    }

    public void addChannel(Channel channel) {
        this.channels.add(channel);
    }

    public boolean hasChannels() {
        return channels != null && !channels.isEmpty();
    }
}
