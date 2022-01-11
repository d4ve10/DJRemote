package com.d4ve10.djremote.model.Impl;

import com.d4ve10.djremote.model.BluetoothProperties;

import java.util.ArrayList;
import java.util.List;

public class BluetoothPropertiesImpl<S> implements BluetoothProperties<S> {

    private final List<S> sockets;

    public BluetoothPropertiesImpl() {
        sockets = new ArrayList<>();
    }

    @Override
    public void addSocket(S socket) {
        sockets.add(socket);
    }

    @Override
    public void removeSocket(S socket) {
        sockets.remove(socket);
    }

    @Override
    public void clearSockets() {
        sockets.clear();
    }

    @Override
    public List<S> getSockets() {
        return sockets;
    }

}
