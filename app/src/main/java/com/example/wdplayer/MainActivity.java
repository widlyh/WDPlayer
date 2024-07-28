package com.example.wdplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivityTag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        request_Permission();
        Init();
    }

    private void Init() {
        Button mediaPlay = findViewById(R.id.btn_play);
        mediaPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()){
                    case R.id.btn_play:
                        Intent intent = null;
                        intent = new Intent(MainActivity.this, MediaPlayerActivity.class);
                        startActivity(intent);
                default:
                    break;
                }
            }
        });
    }

//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.button:
//                Toast.makeText(this,"按钮被点击了",Toast.LENGTH_SHORT).show();
//            default:
//                break;
//        }
//    }

    // lyh add for permission
    private void request_Permission(){
        requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 0xbb);
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0xbb);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            if(!Environment.isExternalStorageManager()){
                Intent intent = new Intent((Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION));
                startActivity(intent);
                return;
            }
        }
    }
}