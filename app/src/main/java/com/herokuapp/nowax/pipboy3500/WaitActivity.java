package com.herokuapp.nowax.pipboy3500;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

class WaitAnimation {
    private AnimationDrawable animation;

    public void start(final ImageView img) {
        img.setBackgroundResource(R.drawable.wait);
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

public class WaitActivity extends Activity {
    private WaitAnimation waitAnimation = new WaitAnimation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity();
        final ImageView img = (ImageView) findViewById(R.id.imageViewWait);
        waitAnimation.start(img);
    }

    public void onUserInteraction() {
        super.onUserInteraction();
        waitAnimation.stop();
        startActivity(new Intent(this, LoadingActivity.class));
        overridePendingTransition(0, 0);
    }

    private void initializeActivity() {
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        setContentView(R.layout.activity_wait);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}
