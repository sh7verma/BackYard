package com.app.backyard;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;


public class PlayVideo extends AppCompatActivity {

    VideoView mVideoview;

    Uri mVideopath;
    int mVideoseek = 0, mUpdatestatic = 0;
    ProgressDialog progDailog;
    MediaController mc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        mVideoview = (VideoView) findViewById(R.id.vv_tull);
        mc = new MediaController(PlayVideo.this);
        mc.setAnchorView(mVideoview);
        progDailog = ProgressDialog.show(this,getString(R.string.Please_wait), getString(R.string.Buffering_Video), true);

        mVideopath = Uri.parse(getIntent().getStringExtra("video_path"));

        mVideoview.setMediaController(mc);
        mVideoview.setVideoURI(mVideopath);
        mVideoview.requestFocus();
        mVideoview.start();

        mVideoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                progDailog.dismiss();
            }
        });

        mVideoview.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        finish();
    }

    @Override
    protected void onDestroy() {
        mVideoview.pause();
        mVideoview = null;
        super.onDestroy();
    }
}
