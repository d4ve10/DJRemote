package com.d4ve10.djremote.control.Impl;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import com.d4ve10.djremote.DJRemote;
import com.d4ve10.djremote.control.ConnectionHandler;
import com.d4ve10.djremote.model.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConnectionHandlerImpl implements ConnectionHandler {
    private Handler handler;
    private final Map<BluetoothSocket, ConnectedThread> connectedThreads;

    public ConnectionHandlerImpl() {
        connectedThreads = new HashMap<>();
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void handleConnection(BluetoothSocket socket) {
        if (connectedThreads.containsKey(socket) && connectedThreads.get(socket) != null && connectedThreads.get(socket).mmSocket.isConnected()) {
            Log.i(Constants.TAG, "Already connected to this socket");
            return;
        }
        ConnectedThread connectedThread = new ConnectedThread(socket);
        connectedThread.start();
        connectedThreads.put(socket, connectedThread);

    }

    public void sendCommand(String command) {
        Log.i(Constants.TAG, "Sending command: " + command);
        byte[] out = command.getBytes();
        write(out);
    }

    public void write(byte[] out) {
        for (ConnectedThread connectedThread : connectedThreads.values()) {
            if (connectedThread != null)
                connectedThread.write(out);
        }
    }

    public void cancel() {
        for(Iterator<Map.Entry<BluetoothSocket, ConnectedThread>> it = connectedThreads.entrySet().iterator(); it.hasNext(); ) {
            ConnectedThread connectedThread = it.next().getValue();
            if (connectedThread != null) {
                it.remove();
                connectedThread.cancel();
            }
        }
    }
    private void cancel(BluetoothSocket socket) {
        if (connectedThreads.containsKey(socket)) {
            ConnectedThread connectedThread = connectedThreads.get(socket);
            if (connectedThread != null) {
                connectedThread.cancel();
                connectedThreads.remove(socket);
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
            } catch (IOException e) {
                Log.e(Constants.TAG, "Error occurred when creating input stream", e);
            }
            try {
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(Constants.TAG, "Error occurred when creating output stream", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] mmBuffer = new byte[1024];
            int numBytes;

            while (true) {
                try {
                    numBytes = mmInStream.read(mmBuffer);
                    String readMessage = new String(mmBuffer, 0, numBytes);
                    Log.d(Constants.TAG, "Received: " + readMessage);
                    MediaControllerServer mediaControllerServer = DJRemote.getDJRemote().getMediaControllerServer();
                    mediaControllerServer.handleCommand(readMessage);
                } catch (IOException e) {
                    Log.d(Constants.TAG, "Input stream was disconnected", e);
                    DJRemote.getDJRemote().getBluetoothProperties().removeSocket(mmSocket);
                    handler.obtainMessage(Constants.CONNECTION_CLOSED, mmSocket.getRemoteDevice()).sendToTarget();
                    ConnectionHandlerImpl.this.cancel(mmSocket);
                    break;
                }
            }
        }

        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Log.e(Constants.TAG, "Error occurred when sending data", e);
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(Constants.TAG, "Could not close the connect socket", e);
            }
        }
    }
}