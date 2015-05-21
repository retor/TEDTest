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

    public void setChannels(ArrayList<Channel> channels) {
        this.channels = channels;
    }

    public void addChannelWithCheck(Channel channel){
        if (hasChannels())
        for (int i = 0; i < channels.size(); i++) {
            String tmpTitle = channels.get(i).getTitle();
            if (channel.getTitle().equalsIgnoreCase(tmpTitle)) {
                channels.remove(i);
                channels.add(i, channel);
            }
        }
    }

    public boolean hasChannels() {
        return channels != null && !channels.isEmpty();
    }
}
