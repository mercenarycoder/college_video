package com.g.videomercenary;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

public class videoplayer extends AppCompatActivity {
private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayer);
        videoView=(VideoView)findViewById(R.id.video_view);
        Uri uri=Uri.parse(getIntent().getStringExtra("uri"));
        videoView.setVideoURI(uri);
        videoView.start();
    }
}
