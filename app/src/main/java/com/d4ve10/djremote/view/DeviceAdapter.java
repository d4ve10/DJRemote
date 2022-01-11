package com.d4ve10.djremote.view;

import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.d4ve10.djremote.R;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {

    protected final List<BluetoothDevice> devices;

    public static class DeviceViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public DeviceViewHolder(TextView v) {
            super(v);
            textView = v;
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public DeviceAdapter(List<BluetoothDevice> devices) {
        this.devices = devices;
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textView = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_text_view, parent, false);
        return new DeviceViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(DeviceViewHolder holder, int position) {
        holder.getTextView().setId(position);
        holder.getTextView().setText(devices.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }
}