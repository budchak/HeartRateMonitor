package com.java.yaroshevich.heartRateMonitor.UI.measurementSchedule;

import com.java.yaroshevich.heartRateMonitor.model.Measurement;

public interface CreateDialogCallback {

    void onSuccess(Measurement measurement);
    void onFailed(String error);
}
