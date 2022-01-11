package com.d4ve10.djremote.control.Impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.d4ve10.djremote.DJRemote;
import com.d4ve10.djremote.model.Constants;
import com.d4ve10.djremote.model.MediaInformation;

public class MediaBroadcast extends BroadcastReceiver {

    private static final IntentFilter intentFilter;
    static {
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.music.metachanged");
        intentFilter.addAction("com.htc.music.metachanged");
        intentFilter.addAction("fm.last.android.metachanged");
        intentFilter.addAction("com.sec.android.app.music.metachanged");
        intentFilter.addAction("com.nullsoft.winamp.metachanged");
        intentFilter.addAction("com.amazon.mp3.metachanged");
        intentFilter.addAction("com.miui.player.metachanged");
        intentFilter.addAction("com.real.IMP.metachanged");
        intentFilter.addAction("com.sonyericsson.music.metachanged");
        intentFilter.addAction("com.rdio.android.metachanged");
        intentFilter.addAction("com.samsung.sec.android.MusicPlayer.metachanged");
        intentFilter.addAction("com.andrew.apollo.metachanged");
        intentFilter.addAction("com.spotify.music.metadatachanged");
    }

    public static IntentFilter getIntentFilter() {
        return intentFilter;
    }

    private Handler handler;
    private final MediaInformation mediaInformation;

    public MediaBroadcast() {
        super();
        mediaInformation = DJRemote.getDJRemote().getMediaInformation();
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                Log.d(Constants.TAG, key + " : " + (bundle.get(key) != null ? bundle.get(key) : "NULL"));
            }
        }
        String artist = intent.getStringExtra("artist");
        String album = intent.getStringExtra("album");
        String track = intent.getStringExtra("track");
        mediaInformation.setArtist(artist);
        mediaInformation.setAlbum(album);
        mediaInformation.setTrack(track);
        Log.d(Constants.TAG, artist + ":" + album + ":" + track);
        if (handler != null) {
            handler.obtainMessage(Constants.MEDIA_UPDATED).sendToTarget();
        }
    }

}
