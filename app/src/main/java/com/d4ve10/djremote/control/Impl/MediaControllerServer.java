package com.d4ve10.djremote.control.Impl;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;

import com.d4ve10.djremote.DJRemote;
import com.d4ve10.djremote.control.MediaController;
import com.d4ve10.djremote.model.Constants;

public class MediaControllerServer implements MediaController {

    private Context context;
    private Handler handler;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void sendMediaInformation() {
        String track = DJRemote.getDJRemote().getMediaInformation().getTrack();
        String artist = DJRemote.getDJRemote().getMediaInformation().getArtist();
        String album = DJRemote.getDJRemote().getMediaInformation().getAlbum();
        int volume = getAudioManager().getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxVolume = getAudioManager().getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        DJRemote.getDJRemote().getConnectionHandler().sendCommand("mediaInfo;" + track + ";" + artist + ";" + album + ";" + volume + ";" + maxVolume);
    }

    private AudioManager getAudioManager() {
        return (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public void handleCommand(String command) {
        if (command.startsWith("mediaInfo;")) {
            String[] split = command.split(";");
            try {
                DJRemote.getDJRemote().getMediaInformation().setTrack(split[1]);
                DJRemote.getDJRemote().getMediaInformation().setArtist(split[2]);
                DJRemote.getDJRemote().getMediaInformation().setAlbum(split[3]);
                DJRemote.getDJRemote().getMediaInformation().setVolume(Integer.parseInt(split[4]));
                DJRemote.getDJRemote().getMediaInformation().setMaxVolume(Integer.parseInt(split[5]));
            } catch (Exception e) {
                Log.e(Constants.TAG, "Error parsing media information: " + e.getMessage());
            }
            if (handler != null) {
                handler.obtainMessage(Constants.MEDIA_UPDATED).sendToTarget();
            }
            return;
        }
        else if (command.startsWith("volume;")) {
            String[] split = command.split(";");
            try {
                setVolume(Integer.parseInt(split[1]));
            } catch (Exception e) {
                Log.e(Constants.TAG, "Error parsing volume: " + e.getMessage());
            }
            return;
        }
        switch (command) {
            case "play":
                play();
                break;
            case "pause":
                pause();
                break;
            case "playPause":
                playPause();
                break;
            case "stop":
                stop();
                break;
            case "next":
                next();
                break;
            case "prev":
                prev();
                break;
            case "fastForward":
                fastForward();
                break;
            case "rewind":
                rewind();
                break;
            default:
                Log.i(Constants.TAG, "Unknown command: " + command);
                break;
        }
    }

    private void sendKeyEvent(int keyEvent) {
        AudioManager mAudioManager = getAudioManager();
        KeyEvent eventDown = new KeyEvent(KeyEvent.ACTION_DOWN, keyEvent);
        KeyEvent eventUp = new KeyEvent(KeyEvent.ACTION_UP, keyEvent);
        mAudioManager.dispatchMediaKeyEvent(eventDown);
        mAudioManager.dispatchMediaKeyEvent(eventUp);
    }


    @Override
    public void play() {
        sendKeyEvent(KeyEvent.KEYCODE_MEDIA_PLAY);
    }

    @Override
    public void pause() {
        sendKeyEvent(KeyEvent.KEYCODE_MEDIA_PAUSE);
    }

    @Override
    public void playPause() {
        sendKeyEvent(KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE);
    }

    @Override
    public void stop() {
        sendKeyEvent(KeyEvent.KEYCODE_MEDIA_STOP);
    }

    @Override
    public void next() {
        sendKeyEvent(KeyEvent.KEYCODE_MEDIA_NEXT);
    }

    @Override
    public void prev() {
        sendKeyEvent(KeyEvent.KEYCODE_MEDIA_PREVIOUS);
    }

    @Override
    public void fastForward() {
        sendKeyEvent(KeyEvent.KEYCODE_MEDIA_FAST_FORWARD);

    }

    @Override
    public void rewind() {
        sendKeyEvent(KeyEvent.KEYCODE_MEDIA_REWIND);
    }

    @Override
    public void setVolume(int volume) {
        AudioManager mAudioManager = getAudioManager();
        Log.d(Constants.TAG, "Setting volume to " + volume);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
        Log.d(Constants.TAG, "Current volume: " + mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
    }

}
