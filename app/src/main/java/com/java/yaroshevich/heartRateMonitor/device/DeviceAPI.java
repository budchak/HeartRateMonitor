package com.java.yaroshevich.heartRateMonitor.device;

import android.content.Context;

import com.java.yaroshevich.heartRateMonitor.device.Interface.DeviceInterface;

import java.util.ArrayList;
import java.util.List;

public class DeviceAPI {

   private static List<DeviceInterface> deviceList;

   private static Context appContext;

   private static Scanner scanner;

   private static Collector collector;

    public static void init(Context context){
        appContext = context;
        if (deviceList == null) {
            deviceList = new ArrayList<>();
        }
    }


    public static Scanner getDeviceScanner(){
        if (scanner == null){
            scanner = new Scanner();
        }
        return scanner;
    }

    private Collector  getCollector(){
        if (collector == null){
            collector = new Collector();
        }
        return collector;
    }

    static Context getAppContext(){
        return appContext;
    }

    public static List<DeviceInterface> getListDevice(){
        if (deviceList == null){
            deviceList = new ArrayList<>();
        }
        return deviceList;
    }

}
