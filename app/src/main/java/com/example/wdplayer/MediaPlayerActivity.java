package com.example.wdplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.IOException;

public class MediaPlayerActivity extends AppCompatActivity {
    private static final String TAG = "MediaPlayerActivitylyh";
    private MediaPlayer mPlayer = null;
    private SurfaceView mSView;
    private SurfaceHolder surfaceHolder;
    private Uri videoUri;
    private String selectedVideoPath = null;
    private static final int VIDEO_FILE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        InitMediaPlayerView();
    }

    private void selectVideo() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("video/*");
        startActivityForResult(intent, VIDEO_FILE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIDEO_FILE && resultCode == RESULT_OK && data != null) {
            videoUri = data.getData();
            Log.d(TAG, "onActivityResult: " + videoUri);
        }
    }

    private void InitMediaPlayerView() {
        mPlayer = new MediaPlayer();
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
            PlayerInit();
            mPlayer.start();
        });
        findViewById(R.id.btn_pause).setOnClickListener(v -> {
            mPlayer.pause();
        });
        findViewById(R.id.btn_stop).setOnClickListener(v -> {
            mPlayer.stop();
        });
        findViewById(R.id.btn_select_video).setOnClickListener(v -> {
            selectVideo();
        });
    }

    private void PlayerInit(){
        try {
            /*
            可设置绝对路径
            mPlayer.setDataSource("/sdcard/DCIM/Camera/2.mp4");
            */
            if(videoUri != null){
                mPlayer.setDataSource(getApplicationContext(),videoUri);
            }
        } catch (IOException e) {
            //弹窗提示
            Toast.makeText(this, "MediaPlayer setDataSouce fail " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
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