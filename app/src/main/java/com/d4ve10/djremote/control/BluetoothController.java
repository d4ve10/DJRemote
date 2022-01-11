package com.d4ve10.djremote.control;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;

import com.d4ve10.djremote.control.Impl.AcceptThread;
import com.d4ve10.djremote.control.Impl.ConnectThread;

import java.util.List;

public interface BluetoothController {

    void start(Activity activity);

    List<BluetoothDevice> getKnownDevices();

    AcceptThread getAcceptThread();

    ConnectThread getConnectThread();

    void discoverDevices(Activity activity);

    void cancelDiscovery();

    void enableDiscoverability(Activity activity);

    void listenForDevices(Handler handler, Activity activity);

    void connectToDevice(BluetoothDevice device);

    void cleanup(Activity activity);
}
