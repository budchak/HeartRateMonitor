package com.java.yaroshevich.heartRateMonitor.cameraAPI;

import android.graphics.ImageFormat;

public class CameraImageFormat {

    private static int format = ImageFormat.JPEG;


    public static int getFormat(){
        return  format;
    }

    public static void newInstance(int imageFormat){
        format = imageFormat;
    }
}
