package com.java.yaroshevich.heartRateMonitor.UI.camera.Camera;

import android.graphics.Bitmap;

public interface CameraResultCallback {


    void onSuccess(Bitmap bitmap);
    void onFailure();
}
