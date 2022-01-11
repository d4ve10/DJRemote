package com.d4ve10.djremote.model.Impl;

import com.d4ve10.djremote.model.MediaInformation;

public class MediaInformationImpl implements MediaInformation {

    private String artist;
    private String album;
    private String track;
    private int volume;
    private int maxVolume;

    public MediaInformationImpl() {
        artist = "";
        album = "";
        track = "";
        volume = 100;
        maxVolume = 100;
    }

    @Override
    public String getArtist() {
        return artist;
    }

    @Override
    public String getAlbum() {
        return album;
    }

    @Override
    public String getTrack() {
        return track;
    }

    @Override
    public int getVolume() {
        return volume;
    }

    @Override
    public int getMaxVolume() {
        return maxVolume;
    }

    @Override
    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public void setAlbum(String album) {
        this.album = album;
    }

    @Override
    public void setTrack(String track) {
        this.track = track;
    }

    @Override
    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public void setMaxVolume(int maxVolume) {
        this.maxVolume = maxVolume;
    }
}
