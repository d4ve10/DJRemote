package com.d4ve10.djremote.control.Impl;

import com.d4ve10.djremote.DJRemote;
import com.d4ve10.djremote.control.ConnectionHandler;
import com.d4ve10.djremote.control.MediaController;

public class MediaControllerClient implements MediaController {
    private final ConnectionHandler connectionHandler;

    public MediaControllerClient() {
        this(DJRemote.getDJRemote().getConnectionHandler());
    }

    public MediaControllerClient(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    @Override
    public void play() {
        connectionHandler.sendCommand("play");
    }

    @Override
    public void pause() {
        connectionHandler.sendCommand("pause");
    }

    @Override
    public void playPause() {
        connectionHandler.sendCommand("playPause");
    }

    @Override
    public void stop() {
        connectionHandler.sendCommand("stop");
    }

    @Override
    public void next() {
        connectionHandler.sendCommand("next");
    }

    @Override
    public void prev() {
        connectionHandler.sendCommand("prev");
    }

    @Override
    public void fastForward() {
        connectionHandler.sendCommand("fastForward");
    }

    @Override
    public void rewind() {
        connectionHandler.sendCommand("rewind");
    }

    @Override
    public void setVolume(int volume) {
        connectionHandler.sendCommand("volume;" + volume);
    }

}
