package com.retor.tedtest.main.ted.mediafragment;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import com.parser.beans.Item;
import com.parser.beans.Media;
import com.retor.tedtest.main.R;
import com.retor.tedtest.main.ted.DialogsBuilder;

/**
 * Created by retor on 13.05.2015.
 */
public class MFragment extends DialogFragment implements Choicer<Media> {

    private ListView listView;
    private VideoView videoView;
    private Item item;
    private ProgressDialog pd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.item = (Item)getArguments().getSerializable("item");
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.choise_videoview, container, false);
        pd = DialogsBuilder.createProgress(v.getContext(), "Buffering video...");
        listView = (ListView) v.findViewById(R.id.listView_media);
        videoView = (VideoView) v.findViewById(R.id.videoView);
        ((TextView)v.findViewById(R.id.media_description)).setText(Html.fromHtml(item.getDescription()));
        v.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    videoView.stopPlayback();
                    videoView.destroyDrawingCache();
                    videoView.suspend();
                }
                dismiss();
            }
        });
        listView.setAdapter(new AdapterMedia(item.getContent(), this));
        videoView.setVisibility(View.INVISIBLE);
//        mc.setAnchorView(videoView);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                MediaController mc = new MediaController(videoView.getContext());
                videoView.setMediaController(mc);
                mc.setAnchorView(videoView);
                mc.setMediaPlayer(videoView);
                videoView.start();
                pd.dismiss();
                mc.show(99999990);
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                dismiss();
            }
        });
        return v;
    }

    @Override
    public void onChose(Media med) {
        pd.show();
        startVideo(med.getUrl());
    }

    private void startVideo(String url) {
        listView.setVisibility(View.INVISIBLE);
        videoView.setVisibility(View.VISIBLE);
        videoView.setVideoURI(Uri.parse(url));
    }

    @Override
    public void dismiss() {
        super.dismiss();

    }
}
