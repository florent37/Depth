package com.github.florent37.depth.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.followUserInput).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(FollowUserInputActivity.class);
            }
        });

        findViewById(R.id.autoAnimate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(AutoAnimateActivity.class);
            }
        });
    }

    private void start(Class theClass) {
        startActivity(new Intent(this, theClass));
    }

}
