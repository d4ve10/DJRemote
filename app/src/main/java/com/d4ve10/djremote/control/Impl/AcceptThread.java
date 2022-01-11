package com.d4ve10.djremote.control.Impl;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import com.d4ve10.djremote.DJRemote;
import com.d4ve10.djremote.model.Constants;

import java.io.IOException;

public class AcceptThread extends Thread {
    private final BluetoothServerSocket serverSocket;
    private final Handler handler;

    public AcceptThread(Handler handler) {
        BluetoothServerSocket tmp = null;
        this.handler = handler;
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        try {
            tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("DJ Remote", Constants.BT_MODULE_UUID);
        } catch (IOException e) {
            Log.e(Constants.TAG, "Socket's listen() method failed", e);
        }
        serverSocket = tmp;
    }

    public void run() {
        if (serverSocket == null) {
            Log.e(Constants.TAG, "Socket is null");
            return;
        }
        while (true) {
            BluetoothSocket bluetoothSocket = null;
            try {
                bluetoothSocket = serverSocket.accept();
                Log.i(Constants.TAG, "Socket opened");
            } catch (IOException e) {
                Log.e(Constants.TAG, "Socket's accept() method failed", e);
                break;
            }

            if (bluetoothSocket != null) {
                Log.i(Constants.TAG, "A connection has been established: " + bluetoothSocket.getRemoteDevice().getName());
                DJRemote.getDJRemote().getBluetoothProperties().addSocket(bluetoothSocket);
                handler.obtainMessage(Constants.CONNECTION_ESTABLISHED, bluetoothSocket.getRemoteDevice()).sendToTarget();
            }
        }
    }

    public void cancel() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            Log.e(Constants.TAG, "Could not close the connect socket", e);
        }
    }
}
