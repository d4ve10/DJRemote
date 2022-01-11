package com.d4ve10.djremote.control.Impl;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;

import com.d4ve10.djremote.DJRemote;

public class VolumeObserver extends ContentObserver {

    private final AudioManager audioManager;

    public VolumeObserver(Context context, Handler handler) {
        super(handler);
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public boolean deliverSelfNotifications() {
        return false;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        int volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        DJRemote.getDJRemote().getMediaInformation().setVolume(volume);
        DJRemote.getDJRemote().getMediaInformation().setMaxVolume(maxVolume);
        DJRemote.getDJRemote().getMediaControllerServer().sendMediaInformation();
    }
}
