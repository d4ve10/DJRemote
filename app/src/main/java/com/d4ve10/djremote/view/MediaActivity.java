package com.d4ve10.djremote.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.d4ve10.djremote.DJRemote;
import com.d4ve10.djremote.R;
import com.d4ve10.djremote.control.MediaController;
import com.d4ve10.djremote.model.Constants;
import com.d4ve10.djremote.model.MediaInformation;


public class MediaActivity extends AppCompatActivity {

    private TextView trackTitle;
    private TextView artist;
    private SeekBar volumeBar;

    private MediaController mediaController;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_activity);

        trackTitle = findViewById(R.id.track_title);
        artist = findViewById(R.id.artist);
        volumeBar = findViewById(R.id.volume_bar);
        volumeBar.setOnSeekBarChangeListener(seekBarVolumeChangeListener);

        handler = getHandler();
        mediaController = DJRemote.getDJRemote().getMediaControllerClient();
        DJRemote.getDJRemote().getMediaControllerServer().setHandler(handler);
        DJRemote.getDJRemote().getConnectionHandler().setHandler(handler);
        updateMediaInformation();
    }

    private Handler getHandler() {
        return new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Constants.CONNECTION_CLOSED:
                        finish();
                        break;
                    case Constants.MEDIA_UPDATED:
                        updateMediaInformation();
                }
            }
        };
    }

    private final SeekBar.OnSeekBarChangeListener seekBarVolumeChangeListener = new SeekBar.OnSeekBarChangeListener() {
        int volume = 100;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                volume = progress;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) { }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            mediaController.setVolume(volume);
        }
    };

    private void updateMediaInformation() {
        MediaInformation mediaInformation = DJRemote.getDJRemote().getMediaInformation();
        trackTitle.setText(mediaInformation.getTrack());
        artist.setText(mediaInformation.getArtist());
        volumeBar.setMax(mediaInformation.getMaxVolume());
        volumeBar.setProgress(mediaInformation.getVolume());
    }

    public void onPlayPause(View view) {
        mediaController.playPause();
    }

    public void onStop(View view) {
        mediaController.stop();
    }

    public void onNext(View view) {
        mediaController.next();
    }

    public void onPrevious(View view) {
        mediaController.prev();
    }

    public void onFastForward(View view) {
        mediaController.fastForward();
    }

    public void onRewind(View view) {
        mediaController.rewind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DJRemote.getDJRemote().getBluetoothController().cleanup(this);
    }
}