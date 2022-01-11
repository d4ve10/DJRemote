package com.d4ve10.djremote.control;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;

public interface ConnectionHandler {

    void setHandler(Handler handler);

    void handleConnection(BluetoothSocket socket);

    void sendCommand(String command);

    void write(byte[] out);

    void cancel();
}
