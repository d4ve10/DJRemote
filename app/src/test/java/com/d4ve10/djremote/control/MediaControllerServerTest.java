package com.d4ve10.djremote.control;

import android.content.Context;
import android.media.AudioManager;
import android.view.KeyEvent;


import com.d4ve10.djremote.control.Impl.MediaControllerServer;

import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;


public class MediaControllerServerTest {

    @Test
    public void testStopKeyPressed() {
        MediaControllerServer server = new MediaControllerServer();
        Context context = Mockito.mock(Context.class);
        AudioManager audioManager = Mockito.mock(AudioManager.class);
        Mockito.when(context.getSystemService(Context.AUDIO_SERVICE)).thenReturn(audioManager);
        server.setContext(context);
        server.stop();
        Mockito.verify(audioManager, Mockito.times(2)).dispatchMediaKeyEvent(ArgumentMatchers.isA(KeyEvent.class));
    }

    @Test
    public void testSkipKeyPressed() {
        MediaControllerServer server = new MediaControllerServer();
        Context context = Mockito.mock(Context.class);
        AudioManager audioManager = Mockito.mock(AudioManager.class);
        Mockito.when(context.getSystemService(Context.AUDIO_SERVICE)).thenReturn(audioManager);
        server.setContext(context);
        server.next();
        Mockito.verify(audioManager, Mockito.times(2)).dispatchMediaKeyEvent(ArgumentMatchers.isA(KeyEvent.class));
    }
}