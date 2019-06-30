package com.java.yaroshevich.heartRateMonitor.UI.measurmentType;

import com.java.yaroshevich.heartRateMonitor.UI.base.BaseInterface;

import java.util.List;

public interface IMeasurementType  {

    interface View extends BaseInterface.View {
        void navigateTo();
        void showTypes(List<String> types);
        void navigateToWithData(int viewId, String data);
    }

    interface Presenter{
        void onItemClick(int id);
        void onError();
    }
}
