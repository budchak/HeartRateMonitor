package com.java.yaroshevich.heartRateMonitor.UI.base;

import com.java.yaroshevich.heartRateMonitor.UI.main.MainInterface;

public class BasePresenter<V extends BaseInterface.View> implements BaseInterface.Presenter<V>{

    protected V view;

    @Override
    public void onAttach(V view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

}
