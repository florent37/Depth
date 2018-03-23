package com.github.florent37.depth.sample.fullscreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.florent37.depth.view.DepthRelativeLayout;
import com.github.florent37.depth.sample.R;

public class FullScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.depth_activity_fullscreen);
    }

}
