package com.java.yaroshevich.heartRateMonitor.repository;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraAccessException;

import com.java.yaroshevich.heartRateMonitor.cameraAPI.CameraAPIManager;
import com.java.yaroshevich.heartRateMonitor.cameraAPI.callback.ResultCallback;

public class PulseService implements ResultCallback {

    private Context context;
    CameraAPIManager manager;
    private ResultCallback callback;

    public PulseService(Context context, ResultCallback callback) {
        manager = new CameraAPIManager(context);
        manager.configCamera();
        this.callback = callback;

    }

    public void startTakePicture() {
        try {
            manager.StartTakePicture(this);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void stopTakePicture() {
        manager.stopTakePicture();
    }

    @Override
    public void onImageAvailable(Bitmap bitmap) {
        callback.onImageAvailable(bitmap);
    }

    @Override
    public void onError(String error) {

    }


}
