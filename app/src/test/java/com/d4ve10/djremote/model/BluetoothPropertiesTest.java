package com.d4ve10.djremote.model;

import com.d4ve10.djremote.model.Impl.BluetoothPropertiesImpl;

import org.junit.Test;

public class BluetoothPropertiesTest {

    @Test
    public void testAddParameter() {
        BluetoothProperties<String> bp = new BluetoothPropertiesImpl<>();
        bp.addSocket("test");
        bp.addSocket("test2");
        bp.addSocket("test3");
        assert(bp.getSockets().size() == 3);
        assert(bp.getSockets().get(0).equals("test"));
        assert(bp.getSockets().get(1).equals("test2"));
        assert(bp.getSockets().get(2).equals("test3"));
    }

    @Test
    public void testRemoveParameter() {
        BluetoothProperties<String> bp = new BluetoothPropertiesImpl<>();
        bp.addSocket("test");
        bp.addSocket("test2");
        bp.addSocket("test3");
        assert(bp.getSockets().size() == 3);
        bp.removeSocket("test2");
        assert(bp.getSockets().size() == 2);
        assert(bp.getSockets().get(0).equals("test"));
        assert(bp.getSockets().get(1).equals("test3"));
    }

    @Test
    public void testClearParameters() {
        BluetoothProperties<String> bp = new BluetoothPropertiesImpl<>();
        bp.addSocket("test");
        bp.addSocket("test2");
        bp.addSocket("test3");
        assert(bp.getSockets().size() == 3);
        bp.clearSockets();
        assert(bp.getSockets().size() == 0);
    }
}
