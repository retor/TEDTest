package com.retor.tedtest.main.fragments.video;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;
import com.retor.tedtest.main.R;
import com.retor.tedtest.main.help.DialogsBuilder;

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
        videoView.setOnTouchListener(new View.OnTouchListener() {
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
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                if (dialog.isShowing())
                    dialog.dismiss();
                getAlertDialog().show();
                return true;
            }
        });
    }

    private android.app.AlertDialog getAlertDialog() {
        android.app.AlertDialog al = DialogsBuilder.createAlert(VideoPlay.this, "Bad file or connection is lost");
        al.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                VideoPlay.this.finish();
            }
        });
        return al;
    }

    @Override
    protected void onDestroy() {
        if (vv.isPlaying()) {
            videoStop();
        }
        super.onDestroy();
    }

    private void videoStop() {
        vv.suspend();
        vv.stopPlayback();
        vv.destroyDrawingCache();
    }

    @Override
    public void onBackPressed() {
        if (vv.isPlaying()) {
            videoStop();
        }
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("position", vv.getCurrentPosition());
        super.onSaveInstanceState(outState);
    }
}
