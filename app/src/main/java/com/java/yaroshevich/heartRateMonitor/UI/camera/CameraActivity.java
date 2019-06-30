// This activity displays camera output on the screen and detects a heartbeat from the blood
// oscillations present in the finger (placed on the camera lens) when the torch is enabled.  A
// bitmap is created from the texture (screen) in order to gather luminance information and
// detect a heart rate from the oscillations in the luminance values.

package com.java.yaroshevich.heartRateMonitor.UI.camera;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import com.java.yaroshevich.heartRateMonitor.App;
import com.java.yaroshevich.heartRateMonitor.FireBase.FirebaseAPI;
import com.java.yaroshevich.heartRateMonitor.R;
import com.java.yaroshevich.heartRateMonitor.UI.camera.saveResult.ResultSaverCallback;
import com.java.yaroshevich.heartRateMonitor.UI.camera.saveResult.ResultSaverFragment;
import com.java.yaroshevich.heartRateMonitor.UI.measurmentType.MeasurementSequenceListActivity;
import com.java.yaroshevich.heartRateMonitor.UI.navigation.NavigationDrawerActivity;
import com.java.yaroshevich.heartRateMonitor.model.Measurement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class CameraActivity extends NavigationDrawerActivity implements CameraInterface.View, ResultSaverCallback {
    protected static final String TAG = "CameraActivity";
    protected TextureView textureView; //TextureView to deploy camera data
    protected String cameraId;
    protected CameraDevice cameraDevice;
    protected CameraCaptureSession cameraCaptureSessions;
    protected CaptureRequest.Builder captureRequestBuilder;
    protected Size imageDimension;
    protected static final int REQUEST_CAMERA_PERMISSION = 1;
    // Thread handler member variables
    protected Handler mBackgroundHandler;
    protected HandlerThread mBackgroundThread;
    protected Button navigateButton;
    protected Button startMeasurmentButton;

    protected CameraPresenter presenter;

    //Heart rate detector member variables
    protected int mHeartRateInBPM;
    protected int mCurrentRollingAverage;
    protected int mLastRollingAverage;
    protected int mLastLastRollingAverage;
    protected long[] mTimeArray;
    protected int numCaptures = 0;
    protected int mNumBeats = 0;
    protected TextView tv;
    protected LineChart chart;
    protected ChartCreator chartCreator;
    protected ProgressBar progressBar;

    protected int i = 0;

    protected List<Integer> data = new ArrayList<>();
    protected List<Entry> entries = new ArrayList<>();
    protected Deque<Integer> deque = new LinkedList<>();
    protected Deque<Long> bits = new LinkedList<>();

    public String currentTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);


        textureView = findViewById(R.id.texture);
        assert textureView != null;
        textureView.setSurfaceTextureListener(textureListener);

        progressBar = findViewById(R.id.ticrate_progressbar);
        progressBar.setMax(14);
        progressBar.setProgress(0);

        presenter = new CameraPresenter(((App) getApplicationContext()).getRepository(), this);

        Bundle arguments = getIntent().getExtras();
        getBundle(arguments);

        presenter.onAttach(this);


        tv = (TextView) findViewById(R.id.neechewalatext);


        startMeasurmentButton = findViewById(R.id.startMeasurementButton);
        startMeasurmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearStartParameter();
                presenter.onButtonClick();
            }
        });

        chart = findViewById(R.id.heartRateLineChar);
        chartCreator = new ChartCreator(chart);
        chartCreator.init();


    }

    protected void clearStartParameter() {
        mTimeArray = new long[15];
        mHeartRateInBPM = 0;
        mCurrentRollingAverage = 0;
        mLastRollingAverage = 0;
        mLastLastRollingAverage = 0;
        numCaptures = 0;
        mNumBeats = 0;
        data = new ArrayList<>();
        entries = new ArrayList<>();
        deque = new LinkedList<>();
        bits = new LinkedList<>();
    }

    protected void getBundle(Bundle arguments) {

        if (arguments != null) {
            String s = arguments.getString(MeasurementSequenceListActivity.MEASUREMENT_TYPE);
            setTitle(s);
        }
    }

    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

            //openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {


            calcAverageArray();

        }
    };

    public void calcAverageArray() {
        Bitmap bmp = textureView.getBitmap();
        int width = bmp.getWidth();
        int height = bmp.getHeight();

        int[] pixels = new int[height * width];

        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        int sum = 0;
        for (int i = 0; i < height * width; i++) {
            int red = (pixels[i] >> 16) & 0xFF;
            sum = sum + red;
        }
        i++;


        if (i < 50) {
            deque.addLast(sum);
        } else {
            deque.addLast(sum);
            deque.removeFirst();
        }

        data = (List<Integer>) deque;
        entries.clear();
        i = 0;
        for (int e : data) {
            i++;
            entries.add(new Entry(i, e));
        }


        chartCreator.setData(entries);


        //chart.setDrawBorders(false);
        // Waits 20 captures, to remove startup artifacts.  First average is the sum.
        if (numCaptures == 20) {
            mCurrentRollingAverage = sum;
            Log.e(TAG, "20");
        }
        // Next 18 averages needs to incorporate the sum with the correct N multiplier
        // in rolling average.
        else if (numCaptures > 20 && numCaptures < 49) {
            mCurrentRollingAverage = (mCurrentRollingAverage * (numCaptures - 20) + sum) / (numCaptures - 19);
            Log.e(TAG, ">20");
        }
        //
        else if (numCaptures >= 49) {

            mCurrentRollingAverage = (mCurrentRollingAverage * 29 + sum) / 30;


            if (mLastRollingAverage > mCurrentRollingAverage && mLastRollingAverage > mLastLastRollingAverage && mNumBeats < 15) {
                if (mNumBeats < 14) {
                    bits.addLast(System.currentTimeMillis());
                } else {
                    bits.addLast(System.currentTimeMillis());
                }
                progressBar.setProgress(mNumBeats);
                List<Long> data = (List<Long>) bits;
                for (int i = 0; i < data.size(); i++) {
                    mTimeArray[i] = data.get(i);
                }

//                    tv.setText("beats="+mNumBeats+"\ntime="+mTimeArray[mNumBeats]);
                mNumBeats++;
                if (mNumBeats >= 15) {
                    Log.e(TAG, "bpm");
                    bits.clear();
                    calcBPM();
                    //closeCamera();
                }

            }
        }
        // Another capture
        numCaptures++;
        // Save previous two values
        mLastLastRollingAverage = mLastRollingAverage;
        mLastRollingAverage = mCurrentRollingAverage;
    }

    protected final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            Log.e(TAG, "onOpened");
            cameraDevice = camera;
            createCameraPreview();
        }

        @Override
        public void onDisconnected(CameraDevice camera) {
            cameraDevice.close();
        }

        @Override
        public void onError(CameraDevice camera, int error) {
            if (cameraDevice != null)
                cameraDevice.close();
            cameraDevice = null;
        }
    };

    // onResume
    protected void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera Background");
        Log.e(TAG, "Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    // onPause
    protected void stopBackgroundThread() {

    }

    protected void calcBPM() {
        int med;
        long[] timedist = new long[14];
        for (int i = 0; i < 14; i++) {


            timedist[i] = mTimeArray[i + 1] - mTimeArray[i];
        }
        Arrays.sort(timedist);
        med = (int) timedist[timedist.length / 2];
        mHeartRateInBPM = 60000 / med;
        TextView tv = (TextView) findViewById(R.id.neechewalatext);
        TextView textView = findViewById(R.id.ticrate_camera_activity_textview);
        textView.setText("" + mHeartRateInBPM);

        Date currentTime = Calendar.getInstance().getTime();
        closeCamera();
        saveToDataBase(mHeartRateInBPM, currentTime.toString());
        //presenter.onError("");
        //cameraDevice.close();


    }

    protected void saveToDataBase(int mHeartRateInBPM, String currentTime) {

        final int h = mHeartRateInBPM;
        this.currentTime = currentTime;

        FirebaseAPI api = ((App) getApplicationContext()).getFirebaseApi();

        Fragment fragment = ResultSaverFragment.newInstance(Integer.toString(h));
        ((ResultSaverFragment) fragment).show(getSupportFragmentManager(), "выбор");

    }

    protected void createCameraPreview() {
        try {
            SurfaceTexture texture = textureView.getSurfaceTexture();
            assert texture != null;
            texture.setDefaultBufferSize(176, 144);
            Surface surface = new Surface(texture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);
            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    if (null == cameraDevice) {
                        return;
                    }
                    cameraCaptureSessions = cameraCaptureSession;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Toast.makeText(CameraActivity.this, "Configuration change", Toast.LENGTH_SHORT).show();
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    // Opening the rear-facing camera for use
    protected void openCamera() {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        Log.e(TAG, "is camera open");
        try {
            cameraId = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                return;
            }
            manager.openCamera(cameraId, stateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "openCamera X");
    }

    protected void updatePreview() {
        if (null == cameraDevice) {
            Log.e(TAG, "updatePreview error, return");
        }
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        captureRequestBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH);
        try {
            cameraCaptureSessions.setRepeatingRequest(captureRequestBuilder.build(), null, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    protected void closeCamera() {
        if (null != cameraDevice) {
            cameraDevice.close();
            cameraDevice = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // close the app
                Toast.makeText(CameraActivity.this, "Sorry!!!, you can't use this app without granting permission", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        Log.e(TAG, "onPause");
        closeCamera();
        stopBackgroundThread();
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void startMeasurement() {
        Log.e(TAG, "onResume");
        startBackgroundThread();
        if (textureView.isAvailable()) {
            openCamera();
        } else {
            textureView.setSurfaceTextureListener(textureListener);
        }
    }

    @Override
    public void showBPM(Bitmap bitmap) {
        Log.d(TAG, "onSurfaceTextureUpdated");
        Bitmap bmp = bitmap;
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        //Log.e(TAG, width + " " + height);
        int[] pixels = new int[height * width];
        // Get pixels from the bitmap, starting at (x,y) = (width/2,height/2)
        // and totaling width/20 rows and height/20 columns
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        int sum = 0;
        for (int i = 0; i < height * width; i++) {
            int red = (pixels[i] >> 16) & 0xFF;
            sum = sum + red;
        }
        i++;


        if (i < 60) {
            deque.addLast(sum);
        } else {
            deque.addLast(sum);
            deque.removeFirst();
        }
        data = (List<Integer>) deque;
        entries.clear();
        i = 0;
        for (int e : data) {
            i++;
            entries.add(new Entry(i, e));
        }
        // turn your data into Entry objects

        LineDataSet dataSet = new LineDataSet(entries, "Label");
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(false);
        dataSet.disableDashedLine();
        dataSet.setDrawHighlightIndicators(false);
        dataSet.disableDashedLine();

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.notifyDataSetChanged();
        chart.invalidate();
        //chart.setDrawBorders(false);
        // Waits 20 captures, to remove startup artifacts.  First average is the sum.
        if (numCaptures == 20) {
            mCurrentRollingAverage = sum;
            Log.e(TAG, "20");
        }
        // Next 18 averages needs to incorporate the sum with the correct N multiplier
        // in rolling average.
        else if (numCaptures > 20 && numCaptures < 49) {
            mCurrentRollingAverage = (mCurrentRollingAverage * (numCaptures - 20) + sum) / (numCaptures - 19);
            Log.e(TAG, ">20");
        }
        // From 49 on, the rolling average incorporates the last 30 rolling averages.
        else if (numCaptures >= 49) {

            mCurrentRollingAverage = (mCurrentRollingAverage * 29 + sum) / 30;


            Log.e(TAG, Integer.toString(mLastRollingAverage) + " " + Integer.toString(mCurrentRollingAverage));
            if (mLastRollingAverage > mCurrentRollingAverage && mLastRollingAverage > mLastLastRollingAverage && mNumBeats < 15) {
                mTimeArray[mNumBeats] = System.currentTimeMillis();
//                    tv.setText("beats="+mNumBeats+"\ntime="+mTimeArray[mNumBeats]);
                mNumBeats++;
                if (mNumBeats == 15) {
                    Log.e(TAG, "bpm");
                    calcBPM();

                }
            }

        }

        // Another capture
        numCaptures++;
        // Save previous two values
        mLastLastRollingAverage = mLastRollingAverage;
        mLastRollingAverage = mCurrentRollingAverage;
    }

    @Override
    public void showMassage() {

    }

    @Override
    public void onSuccess(Measurement measurement) {
        measurement.setDate(currentTime);
        measurement.setName("одиночное");
        presenter.saveResult(measurement);
    }

    @Override
    public void onFailed(String error) {

    }
}


