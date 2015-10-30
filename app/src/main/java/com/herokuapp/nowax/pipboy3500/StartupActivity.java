package com.herokuapp.nowax.pipboy3500;

import android.app.ListActivity;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class Mp3Filter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        return name.endsWith(".mp3");
    }
}

class Radio {
    private int currentRadioStation = -1;
    private boolean isRadioTurnedOn = false;
    private MediaPlayer mp = new MediaPlayer();
    private List<String> songs;
    private String musicPath;

    public Radio(List<String> loaded_songs, String setMusicPath) {
        songs = loaded_songs;
        musicPath = setMusicPath;
    }

    public void start(int position) throws IOException {
        mp.reset();
        mp.setDataSource(musicPath + songs.get(position) + ".mp3");
        mp.prepare();
        mp.seekTo(getRandomTimeStart());
        mp.start();
        currentRadioStation = position;
        isRadioTurnedOn = true;
    }

    public boolean shouldStop(int position) {
        return position == currentRadioStation && isRadioTurnedOn;
    }

    private int getRandomTimeStart() {
        Random r = new Random();
        return r.nextInt(20000000-0);
    }

    public void stop(ImageView img) {
        isRadioTurnedOn = false;
        mp.stop();
        img.setBackgroundResource(R.drawable.radio_background);
    }
}

class RadioNoiseAnimation {
    private AnimationDrawable radioAnimation;

    public void start(final ImageView img) {
        img.setBackgroundResource(R.drawable.radio);
        img.post(new Runnable() {
            @Override
            public void run() {
                radioAnimation = (AnimationDrawable) img.getBackground();
                radioAnimation.start();
            }
        });
    }

    public void stop() {
        radioAnimation.stop();
    }
}

public class StartupActivity extends ListActivity {
    private static final String musicPath = "/sdcard/external_sd/MUZYKA/Radio New Vegas/radio/";
    private List<String> songs = new ArrayList<>();
    private Radio currentRadio;
    private RadioNoiseAnimation noise = new RadioNoiseAnimation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeActivity();
        initializeStopButton();
        initializePlaylist();
        currentRadio = new Radio(songs, musicPath);
    }

    private void initializeActivity() {
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        setContentView(R.layout.activity_startup);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private void initializeStopButton() {
        Button stopButton = (Button) findViewById(R.id.button);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentRadio.stop((ImageView)findViewById(R.id.imageView));
                noise.stop();
            }
        });
    }

    private void initializePlaylist() {
        File rootPath = new File(musicPath);
        if (rootPath.listFiles( new Mp3Filter()).length > 0) {
            for (File file : rootPath.listFiles(new Mp3Filter())) {
                String name = file.getName();
                songs.add(name.substring(0, name.lastIndexOf('.')));
            }

            Collections.sort(songs);
            ArrayAdapter<String> songList = new ArrayAdapter<>(this, R.layout.song_item, songs);
            setListAdapter(songList);
        }
    }

    @Override
    protected void onListItemClick(ListView list, View view, int position,  long id) {
        if (currentRadio.shouldStop(position)) {
            currentRadio.stop((ImageView)findViewById(R.id.imageView));
        } else {
            try {
                currentRadio.start(position);
                noise.start((ImageView)findViewById(R.id.imageView));
            } catch (IOException e) {
                Log.v(getString(R.string.app_name), e.getMessage());
            }
        }
    }
}
