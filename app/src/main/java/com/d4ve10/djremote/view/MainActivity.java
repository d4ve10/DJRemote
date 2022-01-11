package com.d4ve10.djremote.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.d4ve10.djremote.DJRemote;
import com.d4ve10.djremote.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DJRemote.getDJRemote().getBluetoothController().start(this);
    }

    public void hostRoom(View view){
        Intent intent = new Intent(this, HostActivity.class);
        startActivity(intent);
    }

    public void joinRoom(View view){
        Intent intent = new Intent(this, JoinActivity.class);
        startActivity(intent);
    }

}