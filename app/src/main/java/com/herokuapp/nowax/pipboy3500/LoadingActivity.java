package com.herokuapp.nowax.pipboy3500;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

class LoadingAnimation {
    private AnimationDrawable animation;

    public void start(final ImageView img) {
        img.setBackgroundResource(R.drawable.loading);
        img.post(new Runnable() {
            @Override
            public void run() {
                animation = (AnimationDrawable) img.getBackground();
                animation.start();
            }
        });
    }

    public void stop() {
        animation.stop();
    }
}

public class LoadingActivity extends Activity {
    private LoadingAnimation loadingAnimation = new LoadingAnimation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity();
        final ImageView img = (ImageView) findViewById(R.id.imageView2);
        loadingAnimation.start(img);
    }

    public void onUserInteraction() {
        super.onUserInteraction();
        loadingAnimation.stop();
        startActivity(new Intent(LoadingActivity.this, RadioActivity.class));
        overridePendingTransition(0, 0);
    }

    private void initializeActivity() {
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        setContentView(R.layout.activity_loading);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}
