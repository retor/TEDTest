package com.retor.tedtest.main.ted;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Created by retor on 05.05.2015.
 */
public class MViewHolder  extends RecyclerView.ViewHolder{

    private TextView headerText;
    private VideoView mainVideo;
    private TextView description;

    public MViewHolder(View itemView) {
        super(itemView);
    }

    public void setDescription(TextView description) {
        this.description = description;
    }

    public void setHeaderText(TextView headerText) {
        this.headerText = headerText;
    }

    public void setMainVideo(VideoView mainVideo) {
        this.mainVideo = mainVideo;
    }

    public TextView getDescription() {
        return description;
    }

    public TextView getHeaderText() {
        return headerText;
    }

    public VideoView getMainVideo() {
        return mainVideo;
    }
}
