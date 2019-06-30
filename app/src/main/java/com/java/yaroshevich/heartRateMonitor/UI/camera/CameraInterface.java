package com.java.yaroshevich.heartRateMonitor.UI.camera;

import android.graphics.Bitmap;

import com.java.yaroshevich.heartRateMonitor.UI.base.BaseInterface;
import com.java.yaroshevich.heartRateMonitor.model.Measurement;

public interface CameraInterface {

    interface View extends BaseInterface.View{
       void showLoading();
       void hideLoading();
       void startMeasurement();
       void showBPM(Bitmap bitmap);


    }

    interface Presenter{
        void onButtonClick();
        void saveResult(Measurement measurement);

    }
}
