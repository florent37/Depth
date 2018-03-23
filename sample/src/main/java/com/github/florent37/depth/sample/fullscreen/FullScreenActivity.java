package com.github.florent37.depth.sample.fullscreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.florent37.depth.depthview.DepthRelativeLayout;
import com.github.florent37.depth.sample.R;

public class FullScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.depth_activity_fullscreen);

        DepthRelativeLayout depthLayout = findViewById(R.id.depthContainer);
        float depth = 30;
        depthLayout.setDepth(depth * getResources().getDisplayMetrics().density);

        float elevation = 100;
        depthLayout.setCustomShadowElevation(elevation * getResources().getDisplayMetrics().density);

        depthLayout.setRotationX(-10);
        depthLayout.setRotationY(45);

        /*
        final ObjectAnimator rotationY = ObjectAnimator.ofFloat(depthLayout, View.ROTATION_Y, 25, 0, -25);
        rotationY.setDuration(1000);
        rotationY.setRepeatCount(ValueAnimator.INFINITE);
        rotationY.setRepeatMode(ValueAnimator.REVERSE);
        rotationY.start();

        final ObjectAnimator rotationX = ObjectAnimator.ofFloat(depthLayout, View.ROTATION_X, -25, 0, 25);
        rotationX.setDuration(2500);
        rotationX.setRepeatCount(ValueAnimator.INFINITE);
        rotationX.setRepeatMode(ValueAnimator.REVERSE);
        rotationX.start();
        */
    }

}
