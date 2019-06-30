package com.java.yaroshevich.heartRateMonitor.UI.measurementSchedule;

import android.util.Log;

import com.java.yaroshevich.heartRateMonitor.UI.base.BasePresenter;
import com.java.yaroshevich.heartRateMonitor.UI.measurementSchedule.Interface.ScheduleContract;
import com.java.yaroshevich.heartRateMonitor.model.Measurement;
import com.java.yaroshevich.heartRateMonitor.model.Schedule;

public class MeasurementSchedulePresenter extends BasePresenter implements ScheduleContract.Presenter {

    private ScheduleContract.View view;
    private Schedule schedule = new Schedule();


    public MeasurementSchedulePresenter(ScheduleContract.View view) {
        this.view = view;
    }

    @Override
    public void onCreateClick() {

    }

    @Override
    public void onCancelButtonClick() {
    }

    @Override
    public void onAddNewElementClick() {
        view.showCreateView();
    }

    @Override
    public void onRemoveElementClick(int id) {
        view.showRemoveView();
    }

    @Override
    public void onUpdateElementClick(int id) {
        view.showUpdateView();
    }

    @Override
    public void updateSchedule(Schedule schedule) {

    }

    @Override
    public void updateSchedule(Measurement measurement) {
        schedule.addMeasurement(measurement);
        view.showListMeasurement(schedule.getMeasurements());
        Log.e("tag", "presenter");
    }
}
