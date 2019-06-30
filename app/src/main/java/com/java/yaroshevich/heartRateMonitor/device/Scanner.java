package com.java.yaroshevich.heartRateMonitor.device;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;

import com.java.yaroshevich.heartRateMonitor.device.Interface.ScannerCallbackInterface;

public class Scanner {



    public void start(ScannerCallbackInterface scannerCallbackInterface, DeviceType deviceType){
        CameraManager manager = (CameraManager)DeviceAPI.getAppContext().getSystemService(Context.CAMERA_SERVICE);
        try {
            String[] cameraIDList = manager.getCameraIdList();
            CameraCharacteristics cameraCharacteristics;
            for (String cameraID: cameraIDList){
                cameraCharacteristics = manager.getCameraCharacteristics(cameraID);

                scannerCallbackInterface.onDeviceFound(new CamDevice("cam_" + cameraID
                        , "1"
                        , "2"
                        , cameraID
                        , Integer.toString(cameraCharacteristics.get(CameraCharacteristics.LENS_FACING))));

            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }



}
