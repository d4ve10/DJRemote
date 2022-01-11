package com.d4ve10.djremote.model;

import com.d4ve10.djremote.model.Impl.MediaInformationImpl;

import org.junit.Test;

public class MediaInformationTest {

    @Test
    public void testGetArtist() {
        MediaInformation mediaInformation = new MediaInformationImpl();
        mediaInformation.setArtist("artist");
        assert(mediaInformation.getArtist().equals("artist"));
    }

    @Test
    public void testGetAlbum() {
        MediaInformation mediaInformation = new MediaInformationImpl();
        mediaInformation.setAlbum("album");
        assert(mediaInformation.getAlbum().equals("album"));
    }

    @Test
    public void testGetTrack() {
        MediaInformation mediaInformation = new MediaInformationImpl();
        mediaInformation.setTrack("track");
        assert(mediaInformation.getTrack().equals("track"));
    }

    @Test
    public void testGetVolume() {
        MediaInformation mediaInformation = new MediaInformationImpl();
        mediaInformation.setVolume(42);
        assert(mediaInformation.getVolume() == 42);
    }

    @Test
    public void testGetMaxVolume() {
        MediaInformation mediaInformation = new MediaInformationImpl();
        mediaInformation.setMaxVolume(69);
        assert(mediaInformation.getMaxVolume() == 69);
    }
}
