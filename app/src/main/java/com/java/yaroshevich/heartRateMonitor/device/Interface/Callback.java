package com.java.yaroshevich.heartRateMonitor.device.Interface;

public interface Callback {

    void onDeviceInit();
    void onDataRecive(String s);
}
