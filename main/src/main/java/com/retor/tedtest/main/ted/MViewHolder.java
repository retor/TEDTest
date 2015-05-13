package com.retor.tedtest.main.ted;

import android.support.v7.widget.*;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.retor.tedtest.main.R;

/**
 * Created by retor on 05.05.2015.
 */
public class MViewHolder extends RecyclerView.ViewHolder {

    TextView headerText;
    //    VideoView mainVideo;
    TextView description;
    TextView duration;
    TextView pubdate;
    ImageView thumb;
    android.support.v7.widget.CardView card;

    public MViewHolder(View itemView) {
        super(itemView);
        headerText = (TextView) itemView.findViewById(R.id.title);
//        mainVideo = (VideoView)itemView.findViewById(R.id.video);
        description = (TextView) itemView.findViewById(R.id.description);
        duration = (TextView) itemView.findViewById(R.id.duration);
        pubdate = (TextView) itemView.findViewById(R.id.pubdate);
        thumb = (ImageView) itemView.findViewById(R.id.thumb);
        card = (CardView)itemView.findViewById(R.id.card_view);
    }

    public CardView getCard() {
        return card;
    }

    public TextView getDuration() {
        return duration;
    }

    public void setDuration(TextView duration) {
        this.duration = duration;
    }

    public TextView getPubdate() {
        return pubdate;
    }

    public void setPubdate(TextView pubdate) {
        this.pubdate = pubdate;
    }

    public TextView getDescription() {
        return description;
    }

    public void setDescription(TextView description) {
        this.description = description;
    }

    public TextView getHeaderText() {
        return headerText;
    }

    public void setHeaderText(TextView headerText) {
        this.headerText = headerText;
    }

    public ImageView getThumb() {
        return thumb;
    }

    public void setThumb(ImageView thumb) {
        this.thumb = thumb;
    }
}
