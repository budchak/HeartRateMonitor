package com.java.yaroshevich.heartRateMonitor.UI.measurementSchedule.Interface;

import com.java.yaroshevich.heartRateMonitor.model.Measurement;
import com.java.yaroshevich.heartRateMonitor.model.Schedule;

import java.util.List;

public interface ScheduleContract {

    interface View{
       void showListMeasurement(List<Measurement> measurement);
       void showCreateView();
       void showUpdateView();
       void showRemoveView();
    }

    interface Presenter{
        void onCreateClick();
        void onCancelButtonClick();
        void onAddNewElementClick();
        void onRemoveElementClick(int id);
        void onUpdateElementClick(int id);
        void updateSchedule(Schedule schedule);
        void updateSchedule(Measurement measurement);
    }
}
