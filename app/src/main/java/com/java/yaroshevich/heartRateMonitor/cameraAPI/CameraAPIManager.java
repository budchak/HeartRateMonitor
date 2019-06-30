package com.java.yaroshevich.heartRateMonitor.cameraAPI;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.Size;
import android.view.Surface;


import com.java.yaroshevich.heartRateMonitor.cameraAPI.callback.CameraDeviceStateCallback;
import com.java.yaroshevich.heartRateMonitor.cameraAPI.callback.ResultCallback;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.content.Context.CAMERA_SERVICE;

public class CameraAPIManager {

    private Context context;

    private CameraManager cameraManager;

    private CameraCharacteristics cameraCharacteristics;

    private String mCameraID;

    private CameraDevice.StateCallback stateCallback;

    private int width = 176;
    private int height = 144;

    private ResultCallback callback;

    private ImageReader imageReader;
    int i;


    public CameraAPIManager(Context context) {
        this.context = context;
        i = 0;
        imageReader = ImageReader.newInstance(width, height, CameraImageFormat.getFormat(), 10);
        imageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            @Override
            public void onImageAvailable(ImageReader reader) {
                i++;
                Log.e("tag", "good " + Integer.toString(i));
                Image image = reader.acquireLatestImage();
                if (image != null) {
                    Image.Plane[] planes = image.getPlanes();
                    ByteBuffer buffer = planes[0].getBuffer();
                    buffer.rewind();
                    byte[] data = new byte[buffer.capacity()];
                    buffer.get(data);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    callback.onImageAvailable(bitmap);
                    image.close();
                }

            }
        }, null);
        cameraManager = (CameraManager) context.getSystemService(CAMERA_SERVICE);
        List<Surface> list = new ArrayList<>();
        list.add(imageReader.getSurface());
        stateCallback = new CameraDeviceStateCallback(list);
    }

    public void configCamera() {

        try {
            String[] cameraIDList = findCameraID();
            if (!findBackCamera(cameraIDList)) {
                Log.e(TAG, "don't find back camera");
                return;
            }
            Log.e(TAG, "find back camera");
            findOptimalInputSize();

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }


    }


    private String[] findCameraID() throws CameraAccessException {
        return cameraManager.getCameraIdList();
    }

    private void findOptimalInputSize() {
        StreamConfigurationMap configurationMap =
                cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        Size[] sizesJPEG = configurationMap.getOutputSizes(CameraImageFormat.getFormat());
        Log.e(TAG, "camera with id: " + mCameraID);
        if (sizesJPEG != null) {
            for (Size item : sizesJPEG) {
                Log.e(TAG, "w:" + item.getWidth() + " h:" + item.getHeight());
                if (width > item.getWidth()) {
                    width = item.getWidth();
                }
                if (height > item.getHeight()) {
                    height = item.getHeight();
                }
            }
        } else {
            Log.e(TAG, "camera with id: " + mCameraID + " don`t support JPEG");
        }
    }

    private boolean findBackCamera(String[] cameraIDList) throws CameraAccessException {
        boolean isFind = false;
        for (String cameraID : cameraIDList) {
            cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraID);
            if (isBackCamera(cameraCharacteristics)) {
                isFind = true;
                mCameraID = cameraID;
                Log.e(TAG, "Back camera: " + mCameraID);
                break;
            }
        }
        return isFind;
    }

    private boolean isBackCamera(CameraCharacteristics cameraCharacteristics) {
        if (cameraCharacteristics == null) {
            Log.e(TAG, "camera characteristics is null");
            return false;
        }

        if (cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_BACK) {
            return true;
        }

        return false;
    }

    public boolean StartTakePicture(ResultCallback callback) throws CameraAccessException {
        this.callback = callback;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        cameraManager.openCamera(mCameraID, stateCallback, null);
        return  true;
    }
    public boolean stopTakePicture(){
        stateCallback.onError(null, CameraDevice.StateCallback.ERROR_CAMERA_DEVICE);
        return true;
    }

}
