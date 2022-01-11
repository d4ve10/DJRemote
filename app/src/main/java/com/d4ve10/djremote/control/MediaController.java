package com.d4ve10.djremote.control;

public interface MediaController {

    void play();

    void pause();

    void playPause();

    void stop();

    void next();

    void prev();

    void fastForward();

    void rewind();

    void setVolume(int volume);
}
