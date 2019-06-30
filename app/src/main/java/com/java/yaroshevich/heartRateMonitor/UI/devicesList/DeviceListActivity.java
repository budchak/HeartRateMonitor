package com.java.yaroshevich.heartRateMonitor.UI.devicesList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.java.yaroshevich.heartRateMonitor.App;
import com.java.yaroshevich.heartRateMonitor.R;
import com.java.yaroshevich.heartRateMonitor.UI.base.BaseActivity;
import com.java.yaroshevich.heartRateMonitor.UI.camera.CameraActivity;
import com.java.yaroshevich.heartRateMonitor.device.DeviceAPI;
import com.java.yaroshevich.heartRateMonitor.device.Interface.DeviceInterface;

import java.util.List;

public class DeviceListActivity extends BaseActivity implements ListActivityCantract.View{

    private RecyclerView recyclerView;

    private App app;

    private ImageButton addNewDeviceButton;


    private DeviceListRecyclerViewAdapter adapter;

    private DeviceListPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_devices);

        presenter = new DeviceListPresenter();
        presenter.onAttach(this);


        recyclerView = findViewById(R.id.deviceListRecyclerView);
        addNewDeviceButton = findViewById(R.id.imageButton);
        addNewDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onButtonClick();


            }
        });
        adapter = new DeviceListRecyclerViewAdapter(this, DeviceAPI.getListDevice());
        adapter.setCallbacks(new DeviceListRecyclerViewAdapter.Callback() {
            @Override
            public void onItemClick(int id) {
                startNewActivity();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    public void startNewActivity() {
        Intent i = new Intent(DeviceListActivity.this, CameraActivity.class);
        startActivity(i);
    }

    @Override
    public void showDeviceList(List<DeviceInterface> devicesList) {
        //adapter.setData(devicesList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateDeviceList(DeviceInterface device) {
        List<DeviceInterface> deviceInterfaces = adapter.getData();
        deviceInterfaces.add(device);
        //adapter.setData(deviceInterfaces);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMassage() {

    }
}
