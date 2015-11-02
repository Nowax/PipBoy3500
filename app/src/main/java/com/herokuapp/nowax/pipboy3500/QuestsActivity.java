package com.herokuapp.nowax.pipboy3500;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class QuestsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity();
    }

    private void initializeActivity() {
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        setContentView(R.layout.activity_quests);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public void onStatClick (View v) {
        startActivity(new Intent(this, StatisticActivity.class));
        overridePendingTransition(0, 0);
    }

    public void onRadioClick (View v) {
        startActivity(new Intent(this, RadioActivity.class));
        overridePendingTransition(0, 0);
    }

    public void onSpecialClick (View v) {
        startActivity(new Intent(this, SpecialActivity.class));
        overridePendingTransition(0, 0);
    }

    public void onQuestsClick (View v) {
        startActivity(new Intent(this, QuestsActivity.class));
        overridePendingTransition(0, 0);
    }
}
