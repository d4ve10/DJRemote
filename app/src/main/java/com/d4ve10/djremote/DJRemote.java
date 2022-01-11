package com.d4ve10.djremote;

import android.bluetooth.BluetoothSocket;

import com.d4ve10.djremote.control.BluetoothController;
import com.d4ve10.djremote.control.Impl.BluetoothControllerImpl;
import com.d4ve10.djremote.control.ConnectionHandler;
import com.d4ve10.djremote.control.Impl.ConnectionHandlerImpl;
import com.d4ve10.djremote.control.Impl.MediaControllerClient;
import com.d4ve10.djremote.control.Impl.MediaControllerServer;
import com.d4ve10.djremote.model.BluetoothProperties;
import com.d4ve10.djremote.model.Impl.BluetoothPropertiesImpl;
import com.d4ve10.djremote.model.Impl.MediaInformationImpl;
import com.d4ve10.djremote.model.MediaInformation;


public class DJRemote {
    private static DJRemote singleton = null;
    private ConnectionHandler connectionHandler = null;
    private MediaControllerClient mediaControllerClient = null;
    private MediaControllerServer mediaControllerServer = null;
    private BluetoothController bluetoothController = null;
    private BluetoothProperties<BluetoothSocket> bluetoothProperties = null;
    private MediaInformation mediaInformation = null;

    private DJRemote() {}

    public static DJRemote getDJRemote() {
        if (singleton == null)
            singleton = new DJRemote();
        return singleton;
    }

    public ConnectionHandler getConnectionHandler() {
        if (connectionHandler == null)
            connectionHandler = new ConnectionHandlerImpl();
        return connectionHandler;
    }

    public BluetoothController getBluetoothController() {
        if (bluetoothController == null)
            bluetoothController = new BluetoothControllerImpl();
        return bluetoothController;
    }

    public BluetoothProperties<BluetoothSocket> getBluetoothProperties() {
        if (bluetoothProperties == null)
            bluetoothProperties = new BluetoothPropertiesImpl<>();
        return bluetoothProperties;
    }

    public MediaControllerClient getMediaControllerClient() {
        if (mediaControllerClient == null)
            mediaControllerClient = new MediaControllerClient();
        return mediaControllerClient;
    }

    public MediaControllerServer getMediaControllerServer() {
        if (mediaControllerServer == null)
            mediaControllerServer = new MediaControllerServer();
        return mediaControllerServer;
    }

    public MediaInformation getMediaInformation() {
        if (mediaInformation == null)
            mediaInformation = new MediaInformationImpl();
        return mediaInformation;
    }

}
