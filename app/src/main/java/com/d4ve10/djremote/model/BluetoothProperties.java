package com.d4ve10.djremote.model;

import java.util.List;

public interface BluetoothProperties<S> {

    void addSocket(S socket);

    void removeSocket(S socket);

    void clearSockets();

    List<S> getSockets();

}
