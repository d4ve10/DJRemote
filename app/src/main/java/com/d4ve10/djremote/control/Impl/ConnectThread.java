package com.d4ve10.djremote.control.Impl;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.d4ve10.djremote.DJRemote;
import com.d4ve10.djremote.model.Constants;

import java.io.IOException;

public class ConnectThread extends Thread {
    private final BluetoothSocket bluetoothSocket;

    public ConnectThread(BluetoothDevice device) {
        BluetoothSocket tmp = null;

        try {
            tmp = device.createRfcommSocketToServiceRecord(Constants.BT_MODULE_UUID);
        } catch (IOException e) {
            Log.e(Constants.TAG, "Socket's create() method failed", e);
        }
        bluetoothSocket = tmp;
    }

    public void run() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.cancelDiscovery();

        try {
            bluetoothSocket.connect();
        } catch (IOException connectException) {
            try {
                bluetoothSocket.close();
            } catch (IOException closeException) {
                Log.e(Constants.TAG, "Could not close the client socket", closeException);
            }
            return;
        }
        DJRemote.getDJRemote().getBluetoothProperties().addSocket(bluetoothSocket);
    }

    public void cancel() {
        try {
            bluetoothSocket.close();
        } catch (IOException e) {
            Log.e(Constants.TAG, "Could not close the client socket", e);
        }
    }
}