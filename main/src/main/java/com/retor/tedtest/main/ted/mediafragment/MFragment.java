package com.retor.tedtest.main.ted.mediafragment;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.parserlib.beans.Item;
import com.parserlib.beans.Media;
import com.retor.tedtest.main.R;
import com.retor.tedtest.main.ted.DialogsBuilder;

/**
 * Created by retor on 13.05.2015.
 */
public class MFragment extends DialogFragment implements Choicer<Media> {

    private ListView listView;
    private VideoView videoView;
    private Item item;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.item = (Item)getArguments().getSerializable("item");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.choise_videoview, container, false);
        v.findViewById(R.id.cancel).
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    videoView.stopPlayback();
                    videoView.destroyDrawingCache();
                    videoView.suspend();
                }
                MFragment.this.dismiss();
            }
        });
        listView = (ListView) v.findViewById(R.id.listView_media);
        videoView = (VideoView) v.findViewById(R.id.videoView);
        v.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        listView.setAdapter(new AdapterMedia(item.getContent(), this));
        ((TextView)v.findViewById(R.id.media_description)).setText(Html.fromHtml(item.getDescription()));
        return v;
    }

    @Override
    public void onChose(Media med) {
        final ProgressDialog pd = DialogsBuilder.createProgress(listView.getContext(), "Buffering video...");
        pd.show();
        listView.setVisibility(View.GONE);
        getView().findViewById(R.id.media_description).setVisibility(View.GONE);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                pd.dismiss();
            }
        });
        startVideo(med);
    }

    private void startVideo(Media med) {//TODO!!!!!!!!!!!!!!!!!!! Controller
        videoView.setVisibility(View.VISIBLE);
        final MediaController mc = new MediaController(videoView.getContext());
        mc.setMediaPlayer(videoView);
        mc.setAnchorView(videoView);
        mc.setTop(0);
        videoView.setTop(0);
        mc.setFocusableInTouchMode(true);
        mc.setFocusable(true);
        videoView.setVideoURI(Uri.parse(med.getUrl()));
        videoView.start();
        videoView.seekTo(1);
        mc.show();
        videoView.setFocusable(true);
        videoView.requestFocus(0);
        videoView.setClickable(true);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                dismiss();
            }
        });
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    Log.d("VideoView", "OnTouch"+v.toString());

                    mc.show();
                    mc.show(3000);
                }
                return true;
            }
        });
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("VideoView", "OnClick"+v.toString());
                mc.show();
                mc.show(3000);
            }
        });
    }

}
