package com.java.yaroshevich.heartRateMonitor.UI.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.java.yaroshevich.heartRateMonitor.UI.base.BasePresenter;
import com.java.yaroshevich.heartRateMonitor.UI.main.MainInterface;
import com.java.yaroshevich.heartRateMonitor.cameraAPI.callback.ResultCallback;
import com.java.yaroshevich.heartRateMonitor.model.Measurement;
import com.java.yaroshevich.heartRateMonitor.repository.MeasurementRepository;
import com.java.yaroshevich.heartRateMonitor.repository.PulseService;

import java.util.List;

public class CameraPresenter<V extends CameraInterface.View> extends BasePresenter<V> implements CameraInterface.Presenter, ResultCallback {

    private MeasurementRepository repository;
    private Context context;
    private PulseService service;

    public CameraPresenter(MeasurementRepository repository, Context context) {
        super();
        this.repository = repository;
        this.context = context;
        service = new PulseService(context, this);
    }

    @Override
    public void onButtonClick() {
        view.startMeasurement();
        //service.startTakePicture();
    }

    @Override
    public void saveResult(Measurement measurement) {
       repository.insert(measurement);
    }


    @Override
    public void onImageAvailable(Bitmap bitmap) {
        view.showBPM(bitmap);
    }

    @Override
    public void onError(String error) {
        service.stopTakePicture();
    }


}
