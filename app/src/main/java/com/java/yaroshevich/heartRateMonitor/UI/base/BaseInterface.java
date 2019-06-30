package com.java.yaroshevich.heartRateMonitor.UI.base;

public interface BaseInterface {

    interface View {
        void showMassage();
    }

    interface Presenter<V extends View>{
        void onAttach(V mvpView);

        void onDetach();

    }
}
