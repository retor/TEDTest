package com.retor.tedtest.main.ted;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.parserlib.beans.Item;
import com.parserlib.beans.Media;
import com.retor.tedtest.main.R;

import java.util.ArrayList;

/**
 * Created by retor on 13.05.2015.
 */
public class MFragment extends DialogFragment {

    private ListView listView;
    private VideoView videoView;
    private Button cancel;
    private Item item;
    private LayoutInflater inflater;
    private Context context;
    private MediaController mc;

    public MFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.item = (Item)getArguments().getSerializable("item");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = getActivity().getApplicationContext();
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.choise_videoview, container, false);
        cancel = (Button) v.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
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
        listView.setAdapter(new AdapterMedia(item.getContent()));
        return v;
    }

    private class AdapterMedia extends BaseAdapter {
    private ArrayList<Media> media;

        public AdapterMedia(ArrayList<Media> items) {
            this.media.addAll(items);
        }

        @Override
        public int getCount() {
            return media.size();
        }

        @Override
        public Media getItem(int position) {
            return media.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            View out = convertView;
            if (out == null) {
                out = inflater.inflate(R.layout.media, parent, false);
            }
            RadioButton rb = (RadioButton) out.findViewById(R.id.media_type);
            rb.setText(media.get(position).getBitRate() + " " + (media.get(position).getFileSize()/1024) + " kB");
            rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startVideo(media.get(position));
                }
            });
            return out;
        }

        private void startVideo(Media med) {
            final ProgressDialog pd = getProgressDialog(context, "Buffering video...");
            pd.show();
            mc = new MediaController(videoView.getContext());
            videoView.setMediaController(mc);
            mc.setMediaPlayer(videoView);
            mc.setAnchorView(videoView);
            videoView.setVideoURI(Uri.parse(med.getUrl()));
            listView.setVisibility(View.INVISIBLE);
            videoView.setVisibility(View.VISIBLE);
            videoView.start();
            videoView.seekTo(0);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    pd.dismiss();
                    videoView.requestFocus();
                }
            });
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    dismiss();
                }
            });
        }

        private ProgressDialog getProgressDialog(Context context, String msg) {
            final ProgressDialog pd = new ProgressDialog(context);
            pd.setCancelable(false);
            pd.setMessage(msg);
            return pd;
        }
    }
}
