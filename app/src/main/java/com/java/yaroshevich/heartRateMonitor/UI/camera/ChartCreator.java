package com.java.yaroshevich.heartRateMonitor.UI.camera;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;


import java.util.List;

public class ChartCreator {

    private LineChart chart;
    private LineDataSet dataSet;

    public ChartCreator(LineChart chart) {
        this.chart = chart;
    }

    public boolean init(){
        Description description = new Description();
        description.setText(" ");
        chart.setDescription(description);
        chart.setNoDataText("");
        chart.setDrawBorders(false);
        Legend legend = chart.getLegend();
        legend.setEnabled(false);

        final YAxis yAxisL = chart.getAxisLeft();
        yAxisL.setEnabled(false);


        final YAxis yAxisR = chart.getAxisRight();
        yAxisR.setEnabled(false);


        final XAxis xAxis = chart.getXAxis();
        xAxis.setEnabled(false);

        return true;
    }

    public  boolean setData(List<Entry> entries){
        dataSet = new LineDataSet(entries, "Label");
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(false);
        dataSet.disableDashedLine();
        dataSet.setDrawHighlightIndicators(false);
        dataSet.disableDashedLine();

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.notifyDataSetChanged();
        chart.invalidate();
        return true;
    }

    public void realtimeConfig(){
        final YAxis yAxisL = chart.getAxisLeft();
        yAxisL.setEnabled(true);


        final YAxis yAxisR = chart.getAxisRight();
        yAxisR.setEnabled(true);

        yAxisL.setAxisMinimum(0);
        yAxisR.setAxisMinimum(0);

        yAxisL.setAxisMaximum(240);
        yAxisR.setAxisMaximum(240);
    }


}
