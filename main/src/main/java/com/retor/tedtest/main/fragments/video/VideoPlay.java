package com.retor.tedtest.main.fragments.video;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;
import com.retor.tedtest.main.DialogsBuilder;
import com.retor.tedtest.main.R;

/**
 * Created by retor on 15.05.2015.
 */
public class VideoPlay extends Activity {
    private VideoView vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.video_play);
        final ProgressDialog pd = DialogsBuilder.createProgress(this, "Loading video...");
        vv = (VideoView) findViewById(R.id.videoView2);
        initVideoView(vv, savedInstanceState, pd);
    }

    private void initVideoView(final VideoView videoView, Bundle savedInstanceState, final ProgressDialog dialog) {
        MediaController mc = new MediaController(vv.getContext());
        mc.setAnchorView(videoView);
        dialog.setCancelable(true);
        dialog.show();
        videoView.setMediaController(mc);
        videoView.setVideoURI(Uri.parse(getIntent().getStringExtra("url")));
        int prevPosition = 0;
        if (savedInstanceState != null && savedInstanceState.getInt("position") > 0) {
            prevPosition = savedInstanceState.getInt("position");
            videoView.seekTo(prevPosition);
        }
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                dialog.dismiss();
                mp.start();
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                onDestroy();
            }
        });
        vv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (videoView.isPlaying())
                        videoView.pause();
                    else
                        videoView.start();
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (vv.isPlaying()) {
            videoStop();
        }
    }

    private void videoStop() {
        vv.suspend();
        vv.stopPlayback();
        vv.destroyDrawingCache();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (vv.isPlaying())
            videoStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("position", vv.getCurrentPosition());
        super.onSaveInstanceState(outState);
    }
}
