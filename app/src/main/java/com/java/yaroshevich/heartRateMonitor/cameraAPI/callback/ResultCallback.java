package com.java.yaroshevich.heartRateMonitor.cameraAPI.callback;

import android.graphics.Bitmap;

public interface ResultCallback {

    void onImageAvailable(Bitmap bitmap);
    void onError(String error);
}
