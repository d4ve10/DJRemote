package com.d4ve10.djremote.model;


public interface MediaInformation {

    String getArtist();

    String getAlbum();

    String getTrack();

    int getVolume();

    int getMaxVolume();

    void setArtist(String artist);

    void setAlbum(String album);

    void setTrack(String track);

    void setVolume(int volume);

    void setMaxVolume(int maxVolume);
}
