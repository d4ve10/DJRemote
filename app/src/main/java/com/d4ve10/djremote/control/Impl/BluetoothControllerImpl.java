package com.d4ve10.djremote.control.Impl;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.d4ve10.djremote.DJRemote;
import com.d4ve10.djremote.control.BluetoothController;
import com.d4ve10.djremote.model.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Set;

public class BluetoothControllerImpl implements BluetoothController {
    private AcceptThread acceptThread;
    private ConnectThread connectThread;
    private final BluetoothAdapter bluetoothAdapter;
    private ContentObserver volumeObserver;

    public BluetoothControllerImpl() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        acceptThread = null;
        connectThread = null;
    }

    @Override
    public void start(Activity activity) {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(enableBtIntent, 1);
    }

    @Override
    public List<BluetoothDevice> getKnownDevices() {
        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        return new ArrayList<>(devices);
    }

    @Override
    public AcceptThread getAcceptThread() {
        return acceptThread;
    }

    @Override
    public ConnectThread getConnectThread() {
        return connectThread;
    }

    @Override
    public void discoverDevices(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},1);
        }
        ActivityCompat.requestPermissions(activity, new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION},
                1);
        cancelDiscovery();
        if (!bluetoothAdapter.startDiscovery()) {
            Toast.makeText(activity, "Discovery failed", Toast.LENGTH_SHORT).show();
            Log.i(Constants.TAG, "Discovery failed");
            return;
        }
        Log.i(Constants.TAG, "Discovering started");
    }

    @Override
    public void cancelDiscovery() {
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
            Log.i(Constants.TAG, "Discovery stopped");
        }
    }

    @Override
    public void enableDiscoverability(Activity activity) {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        activity.startActivity(discoverableIntent);
    }

    @Override
    public void listenForDevices(Handler handler, Activity activity) {
        if (acceptThread != null) {
            acceptThread.cancel();
            acceptThread = null;
        }
        acceptThread = new AcceptThread(handler);
        acceptThread.start();
        volumeObserver = new VolumeObserver(activity.getApplicationContext(), handler);
        activity.getApplicationContext().getContentResolver().registerContentObserver(android.provider.Settings.System.CONTENT_URI, true, volumeObserver);

        IntentFilter filter = MediaBroadcast.getIntentFilter();
        MediaBroadcast mediaBroadcast = new MediaBroadcast();
        mediaBroadcast.setHandler(handler);
        activity.registerReceiver(mediaBroadcast, filter);
    }

    @Override
    public void connectToDevice(BluetoothDevice device) {
        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }
        connectThread = new ConnectThread(device);
        connectThread.start();
    }

    @Override
    public void cleanup(Activity activity) {
        if (acceptThread != null) {
            acceptThread.cancel();
            acceptThread = null;
        }
        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }
        DJRemote.getDJRemote().getBluetoothProperties().clearSockets();
        DJRemote.getDJRemote().getConnectionHandler().cancel();
        if (volumeObserver != null)
            activity.getApplicationContext().getContentResolver().unregisterContentObserver(volumeObserver);
    }
}
