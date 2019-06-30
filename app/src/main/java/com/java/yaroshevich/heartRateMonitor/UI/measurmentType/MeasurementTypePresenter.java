package com.java.yaroshevich.heartRateMonitor.UI.measurmentType;

import com.java.yaroshevich.heartRateMonitor.UI.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;


public class MeasurementTypePresenter extends BasePresenter<IMeasurementType.View> implements IMeasurementType.Presenter {

    private List<String> strings;

    public MeasurementTypePresenter(){
        strings = new ArrayList<>();
        strings.add("одиночное");
        strings.add("непрерывное");
    }

    @Override
    public void onItemClick(int i) {
        view.navigateToWithData(i, strings.get(i));

    }

    @Override
    public void onError() {

    }

    @Override
    public void onAttach(IMeasurementType.View view) {
        super.onAttach(view);
        view.showTypes(strings);
    }
}
