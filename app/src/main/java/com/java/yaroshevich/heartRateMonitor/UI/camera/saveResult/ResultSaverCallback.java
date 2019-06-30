package com.java.yaroshevich.heartRateMonitor.UI.camera.saveResult;

import com.java.yaroshevich.heartRateMonitor.model.Measurement;

public interface ResultSaverCallback {

    void onSuccess(Measurement measurement);
    void onFailed(String error);
}
