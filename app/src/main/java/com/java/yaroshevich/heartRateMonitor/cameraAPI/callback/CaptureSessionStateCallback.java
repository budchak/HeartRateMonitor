package com.java.yaroshevich.heartRateMonitor.cameraAPI.callback;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.support.annotation.NonNull;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class CaptureSessionStateCallback extends CameraCaptureSession.StateCallback {

    private CameraDevice mCameraDevice;
    private CameraCaptureSession mCaptureSession;
    private CaptureRequest.Builder mPreviewRequestBuilder;
    private CaptureRequest mPreviewRequest;

    public CaptureSessionStateCallback(CameraDevice cameraDevice, CaptureRequest.Builder requestBuilder){
        mCameraDevice = cameraDevice;
        mPreviewRequestBuilder = requestBuilder;
    }

    @Override
    public void onConfigured( @NonNull CameraCaptureSession session ) {
        if (null == mCameraDevice) {
            Log.e(TAG, "no camera device");
            return;
        }
        mCaptureSession = session;

        mPreviewRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        mPreviewRequestBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH);
        try {
            mCaptureSession.setRepeatingRequest(mPreviewRequestBuilder.build(), null, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }




        Log.e(TAG, "config session");
    }

    @Override
    public void onConfigureFailed( @NonNull CameraCaptureSession session) {
        Log.e(TAG, "все плохо");
    }
}
