package com.example.wdplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import java.io.IOException;

public class MediaPlayerActivity extends AppCompatActivity {
    private static final String TAG = "MediaPlayerActivity";
    private MediaPlayer mPlayer = null;
    private SurfaceView mSView;
    private SurfaceHolder surfaceHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        Init();
        bindViews();
    }

    private void bindViews() {
        mSView = findViewById(R.id.mSView);
        surfaceHolder = mSView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //设置视频显示在SurfaceView上
                mPlayer.setDisplay(surfaceHolder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {}
        });
        findViewById(R.id.btn_start).setOnClickListener(v -> {
            mPlayer.start();
        });
        findViewById(R.id.btn_pause).setOnClickListener(v -> {
            mPlayer.pause();
        });
        findViewById(R.id.btn_stop).setOnClickListener(v -> {
            mPlayer.stop();
        });
    }

    private void Init(){
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource("/sdcard/DCIM/Camera/2.mp4");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            mPlayer.prepare();//同步，会阻塞
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
        }
        mPlayer.release();
    }
}