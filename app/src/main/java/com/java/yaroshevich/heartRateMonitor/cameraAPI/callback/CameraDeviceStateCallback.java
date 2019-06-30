package com.java.yaroshevich.heartRateMonitor.cameraAPI.callback;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Surface;

import java.util.List;

public class CameraDeviceStateCallback extends CameraDevice.StateCallback {

    private CameraDevice cameraDevice;
    private List<Surface> surfaces;
    private CaptureSessionStateCallback captureSessionStateCallback;

    private HandlerThread mBackgroundThread;
    private Handler mBackgroundHandler;

    private CaptureRequest.Builder mPreviewRequestBuilder;
    private CaptureRequest mPreviewRequest;


    public CameraDeviceStateCallback(List<Surface> surfaces){
        if (surfaces.size() < 1){
            Log.e("TAG", "surfaces ia empty");

        }else {
            Log.e("tag", "surfaces size = " + Integer.toString(surfaces.size()));
        }
        this.surfaces = surfaces;
        startBackgroundThread();

    }

    @Override
    public void onOpened( @NonNull CameraDevice camera)  {
        this.cameraDevice = camera;

        try {

            mPreviewRequestBuilder
                    = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);

            mPreviewRequestBuilder.addTarget(surfaces.get(0));
            captureSessionStateCallback = new CaptureSessionStateCallback(cameraDevice, mPreviewRequestBuilder);
            cameraDevice.createCaptureSession(surfaces, captureSessionStateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisconnected(@NonNull CameraDevice camera) {
        cameraDevice.close();
        cameraDevice = null;
    }

    @Override
    public void onError(@NonNull CameraDevice camera, int error) {
        cameraDevice.close();
    }

    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("CameraBackground");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }
}
