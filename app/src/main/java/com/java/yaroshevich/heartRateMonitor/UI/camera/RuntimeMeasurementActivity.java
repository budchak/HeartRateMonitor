package com.java.yaroshevich.heartRateMonitor.UI.camera;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.java.yaroshevich.heartRateMonitor.App;
import com.java.yaroshevich.heartRateMonitor.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class RuntimeMeasurementActivity extends CameraActivity {

    private LineChart realTimeChart;
    private ChartCreator realtimeChartCreator;
    protected int g = 0;

    protected List<Integer> realtimeData = new ArrayList<>();
    protected List<Entry> realtimeEntries = new ArrayList<>();
    protected Deque<Integer> realtimeDeque = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime_measurement);

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


        tv = findViewById(R.id.neechewalatext);


        startMeasurmentButton = findViewById(R.id.startMeasurementButton);
        startMeasurmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearStartParameter();
                if (startMeasurmentButton.getText() == "измерить") {
                    presenter.onButtonClick();
                    startMeasurmentButton.setText("остановить");
                } else {
                    startMeasurmentButton.setText("измерить");
                    clearStartParameter();
                    closeCamera();
                }

            }
        });

        realTimeChart = findViewById(R.id.realtimeLineChart);
        realtimeChartCreator = new ChartCreator(realTimeChart);
        realtimeChartCreator.init();


        chart = findViewById(R.id.heartRateLineChar);
        chartCreator = new ChartCreator(chart);
        chartCreator.init();
        chartCreator.realtimeConfig();
    }

    @Override
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
        g++;


        if (g < 50) {
            realtimeDeque.addLast(sum);
        } else {
            realtimeDeque.addLast(sum);
            realtimeDeque.removeFirst();
        }

        realtimeData = (List<Integer>) realtimeDeque;
        realtimeEntries.clear();
        g = 0;
        for (int e : realtimeData) {
            g++;
            realtimeEntries.add(new Entry(g, e));
        }

        realtimeChartCreator.setData(realtimeEntries);

        if (numCaptures == 20) {
            mCurrentRollingAverage = sum;
            Log.e(TAG, "20");
        } else if (numCaptures > 20 && numCaptures < 49) {
            mCurrentRollingAverage = (mCurrentRollingAverage * (numCaptures - 20) + sum) / (numCaptures - 19);
            Log.e(TAG, ">20");
        } else if (numCaptures >= 49) {

            mCurrentRollingAverage = (mCurrentRollingAverage * 29 + sum) / 30;


            if (mLastRollingAverage > mCurrentRollingAverage && mLastRollingAverage > mLastLastRollingAverage) {
                if (mNumBeats < 14) {
                    bits.addLast(System.currentTimeMillis());
                } else {
                    bits.addLast(System.currentTimeMillis());
                    bits.removeFirst();
                }
                progressBar.setProgress(mNumBeats);
                List<Long> data = (List<Long>) bits;
                for (int i = 0; i < data.size(); i++) {
                    mTimeArray[i] = data.get(i);
                }

//
                mNumBeats++;
                if (mNumBeats >= 15) {
                    Log.e(TAG, "bpm");
                    //bits.clear();
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


    @Override
    protected void calcBPM() {

        int med;
        long[] timedist = new long[14];
        for (int i = 0; i < 14; i++) {

            timedist[i] = mTimeArray[i + 1] - mTimeArray[i];
        }
        Arrays.sort(timedist);
        med = (int) timedist[timedist.length / 2];
        mHeartRateInBPM = 60000 / med;
        TextView textView = findViewById(R.id.ticrate_camera_activity_textview);
        textView.setText("" + mHeartRateInBPM);

        if (i < 5) {
            deque.addLast(mHeartRateInBPM);
        } else {
            deque.addLast(mHeartRateInBPM);
            deque.removeFirst();
        }

        data = (List<Integer>) deque;
        entries.clear();
        int p = 0;
        for (int e : data) {
            p++;
            entries.add(new Entry(p, e));
        }


        chartCreator.setData(entries);


    }

    @Override
    protected void clearStartParameter() {
        super.clearStartParameter();

        g = 0;

        realtimeData = new ArrayList<>();
        realtimeEntries = new ArrayList<>();
        realtimeDeque = new LinkedList<>();
    }
}
