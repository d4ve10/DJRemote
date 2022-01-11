package com.d4ve10.djremote.view;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.d4ve10.djremote.DJRemote;
import com.d4ve10.djremote.R;
import com.d4ve10.djremote.control.ConnectionHandler;
import com.d4ve10.djremote.model.Constants;


import java.util.List;

public class JoinActivity extends AppCompatActivity {
    // GUI Components
    private TextView bluetoothStatus;

    private List<BluetoothDevice> availableDevices;
    private JoinDeviceAdapter deviceAdapter;

    private Thread connectThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_activity);

        RecyclerView devicesRecyclerView = findViewById(R.id.join_device_list);
        bluetoothStatus = findViewById(R.id.bluetooth_status);
        availableDevices = DJRemote.getDJRemote().getBluetoothController().getKnownDevices();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        devicesRecyclerView.setLayoutManager(layoutManager);
        deviceAdapter = new JoinDeviceAdapter(availableDevices);
        devicesRecyclerView.setAdapter(deviceAdapter);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        registerReceiver(bluetoothReceiver, filter);
        DJRemote.getDJRemote().getBluetoothController().discoverDevices(this);
    }

    private void manageSocket(BluetoothSocket socket) {
        ConnectionHandler connectionHandler = DJRemote.getDJRemote().getConnectionHandler();
        connectionHandler.handleConnection(socket);
        Intent intent = new Intent(this, MediaActivity.class);
        startActivity(intent);
    }

    private void connectWithDevice(BluetoothDevice device) {
        bluetoothStatus.setText("Connecting...");
        if (connectThread != null) {
            connectThread.interrupt();
        }
        connectThread = new Thread(() -> {
            DJRemote.getDJRemote().getBluetoothController().connectToDevice(device);
            try {
                DJRemote.getDJRemote().getBluetoothController().getConnectThread().join();
            } catch (InterruptedException e) {
                Log.e(Constants.TAG, "Couldn't join connect thread", e);
            }

            List<BluetoothSocket> sockets = DJRemote.getDJRemote().getBluetoothProperties().getSockets();
            runOnUiThread(() -> {
                if (sockets.isEmpty())
                    bluetoothStatus.setText("Couldn't connect to device");
                bluetoothStatus.setText("");
            });
            for (BluetoothSocket socket : sockets) {
                manageSocket(socket);
            }
        });
        connectThread.start();
    }

    private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (availableDevices.contains(device)) {
                    int index = availableDevices.indexOf(device);
                    availableDevices.remove(device);
                    deviceAdapter.notifyItemRemoved(index);
                }
                availableDevices.add(device);
                deviceAdapter.notifyItemChanged(availableDevices.size() - 1);
            }
        }
    };

    public class JoinDeviceAdapter extends DeviceAdapter {

        public JoinDeviceAdapter(List<BluetoothDevice> devices) {
            super(devices);
        }

        @Override
        public DeviceAdapter.DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            TextView textView = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.device_text_view, parent, false);
            View.OnClickListener onClickListener = view -> connectWithDevice(devices.get(view.getId()));
            textView.setOnClickListener(onClickListener);
            return new DeviceViewHolder(textView);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bluetoothReceiver);
        DJRemote.getDJRemote().getBluetoothController().cancelDiscovery();
    }

}