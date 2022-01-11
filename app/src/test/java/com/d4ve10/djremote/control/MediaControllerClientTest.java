package com.d4ve10.djremote.control;

import com.d4ve10.djremote.control.Impl.MediaControllerClient;

import org.junit.Test;
import org.mockito.Mockito;


public class MediaControllerClientTest {

    @Test
    public void testSendingPlayCommand() {
        ConnectionHandler connectionHandler = Mockito.mock(ConnectionHandler.class);
        MediaControllerClient mediaControllerClient = new MediaControllerClient(connectionHandler);
        mediaControllerClient.play();
        Mockito.verify(connectionHandler).sendCommand("play");
    }

    @Test
    public void testSendingFastForwardCommand() {
        ConnectionHandler connectionHandler = Mockito.mock(ConnectionHandler.class);
        MediaControllerClient mediaControllerClient = new MediaControllerClient(connectionHandler);
        mediaControllerClient.fastForward();
        Mockito.verify(connectionHandler).sendCommand("fastForward");
    }
}