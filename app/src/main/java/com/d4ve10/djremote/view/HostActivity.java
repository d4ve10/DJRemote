package com.d4ve10.djremote.view;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.d4ve10.djremote.DJRemote;
import com.d4ve10.djremote.R;
import com.d4ve10.djremote.control.ConnectionHandler;
import com.d4ve10.djremote.control.Impl.MediaControllerServer;
import com.d4ve10.djremote.model.Constants;

import java.util.ArrayList;
import java.util.List;

public class HostActivity extends AppCompatActivity {

    // GUI Components
    private TextView connectionStatus;

    private List<BluetoothDevice> connectedDevices;
    private DeviceAdapter deviceAdapter;
    private Handler hostHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host_activity);

        connectedDevices = new ArrayList<>();
        hostHandler = getHandler();

        connectionStatus = findViewById(R.id.connection_status);
        RecyclerView deviceList = findViewById(R.id.host_device_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        deviceList.setLayoutManager(layoutManager);
        deviceAdapter = new DeviceAdapter(connectedDevices);
        deviceList.setAdapter(deviceAdapter);


        MediaControllerServer mediaControllerServer = DJRemote.getDJRemote().getMediaControllerServer();
        mediaControllerServer.setContext(this.getApplicationContext());
        DJRemote.getDJRemote().getBluetoothController().listenForDevices(hostHandler, this);
    }

    public void enableDiscoverability(View view) {
        DJRemote.getDJRemote().getBluetoothController().enableDiscoverability(this);
    }

    private Handler getHandler() {
        return new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                BluetoothDevice device = null;
                switch (msg.what) {
                    case Constants.CONNECTION_ESTABLISHED:
                        device = (BluetoothDevice) msg.obj;
                        if (!connectedDevices.contains(device))
                            connectedDevices.add(device);
                        deviceAdapter.notifyItemChanged(connectedDevices.size() - 1);
                        updateSockets();
                        DJRemote.getDJRemote().getMediaControllerServer().sendMediaInformation();
                        break;
                    case Constants.CONNECTION_CLOSED:
                        device = (BluetoothDevice) msg.obj;
                        if (connectedDevices.contains(device)) {
                            int index = connectedDevices.indexOf(device);
                            connectedDevices.remove(device);
                            deviceAdapter.notifyItemRemoved(index);
                            updateStatus();
                        }
                        break;
                    case Constants.MEDIA_UPDATED:
                        DJRemote.getDJRemote().getMediaControllerServer().sendMediaInformation();
                }
            }
        };
    }

    private void updateSockets() {
        List<BluetoothSocket> sockets = DJRemote.getDJRemote().getBluetoothProperties().getSockets();
        runOnUiThread(this::updateStatus);
        for (BluetoothSocket socket : sockets) {
            manageSocket(socket);
        }
    }

    private void updateStatus() {
        List<BluetoothSocket> sockets = DJRemote.getDJRemote().getBluetoothProperties().getSockets();
        if (sockets.size() == 1) {
            connectionStatus.setText("1 device is connected");
        }
        else if (sockets.size() > 1)
            connectionStatus.setText(sockets.size() + " devices are connected");
        else
            connectionStatus.setText("No connections");
    }

    private void manageSocket(BluetoothSocket socket) {
        ConnectionHandler connectionHandler = DJRemote.getDJRemote().getConnectionHandler();
        connectionHandler.setHandler(hostHandler);
        connectionHandler.handleConnection(socket);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DJRemote.getDJRemote().getBluetoothController().cleanup(this);
    }
}