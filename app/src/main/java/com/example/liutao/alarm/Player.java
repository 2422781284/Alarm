package com.example.liutao.alarm;


import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;
import java.io.Serializable;
import java.nio.channels.AsynchronousCloseException;


public class Player {
    public String url;
    private MediaPlayer mediaPlayer;

    public Player() {
        mediaPlayer = new MediaPlayer();

    }

    public void begin() {
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void trueBegin() {
        try {
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void endMusic() {
        mediaPlayer.stop();


    }
}
