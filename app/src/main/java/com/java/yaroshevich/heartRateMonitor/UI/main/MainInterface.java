package com.java.yaroshevich.heartRateMonitor.UI.main;

import com.java.yaroshevich.heartRateMonitor.UI.base.BaseInterface;
import com.java.yaroshevich.heartRateMonitor.model.Measurement;

import java.util.List;

public interface MainInterface {

    interface View extends BaseInterface.View {
        void updateStatistic(List<Measurement> measurements);
        void startNewActivity(int activityID);

    }

    interface Presenter{
        void onButtonClick();
        void onReady();
    }
}
