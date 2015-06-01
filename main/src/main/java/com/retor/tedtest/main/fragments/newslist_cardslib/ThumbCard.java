package com.retor.tedtest.main.fragments.newslist_cardslib;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import it.gmariotti.cardslib.library.internal.CardThumbnail;

/**
 * Created by retor on 27.05.2015.
 */
public class ThumbCard extends CardThumbnail {
    public ThumbCard(Context context) {
        super(context);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View imageView) {
        Picasso.with(getContext())
                .load(Uri.parse(urlResource))
                .into((ImageView) imageView);
        imageView.getLayoutParams().width = 250;
        imageView.getLayoutParams().height = 250;
    }
}
