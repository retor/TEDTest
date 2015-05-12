package com.retor.tedtest.main.ted;

import android.support.v7.widget.RecyclerView;
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
    ImageView thumb;

    public MViewHolder(View itemView) {
        super(itemView);
        headerText = (TextView) itemView.findViewById(R.id.title);
//        mainVideo = (VideoView)itemView.findViewById(R.id.video);
        description = (TextView) itemView.findViewById(R.id.description);
        thumb = (ImageView)itemView.findViewById(R.id.thumb);
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
