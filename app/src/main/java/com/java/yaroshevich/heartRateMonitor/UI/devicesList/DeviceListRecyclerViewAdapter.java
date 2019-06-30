package com.java.yaroshevich.heartRateMonitor.UI.devicesList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.java.yaroshevich.heartRateMonitor.R;
import com.java.yaroshevich.heartRateMonitor.device.Interface.DeviceInterface;

import java.util.List;

public class DeviceListRecyclerViewAdapter extends RecyclerView.Adapter<DeviceListRecyclerViewAdapter.ViewHolder> {

    private List<DeviceInterface> deviceInfo;
    private Context context;
    private Callback callbacks;


    public DeviceListRecyclerViewAdapter(Context context, List<DeviceInterface> deviceInfo) {
        this.context = context;
        this.deviceInfo = deviceInfo;

    }

    public DeviceListRecyclerViewAdapter(List<DeviceInterface> deviceInfo, Context context, Callback callbacks) {
        this.deviceInfo = deviceInfo;
        this.context = context;
        this.callbacks = callbacks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_device_info, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
                viewHolder.bind(deviceInfo.get(i));
    }

    @Override
    public int getItemCount() {
        Log.e("log", Integer.toString( deviceInfo.size()));
        return deviceInfo.size();

    }

    public List<DeviceInterface> getData(){
        return deviceInfo;
    }

    public void setData(List<DeviceInterface> deviceInfo){
        this.deviceInfo = deviceInfo;
    }

    public void setCallbacks(Callback callbacks) {
        this.callbacks = callbacks;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        View view;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.deviceNameTextView);
            view = itemView.findViewById(R.id.deviceInfoView);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.setBackgroundColor(0x00FF00FF);
                    callbacks.onItemClick(getAdapterPosition());
                }
            });


        }

        void bind(DeviceInterface info){
            textView.setText(info.getName());
        }
    }

    public interface Callback{
        void onItemClick(int id);
    }


}
