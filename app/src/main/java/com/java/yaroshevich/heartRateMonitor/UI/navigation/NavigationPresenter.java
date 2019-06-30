package com.java.yaroshevich.heartRateMonitor.UI.navigation;

import com.java.yaroshevich.heartRateMonitor.UI.base.BasePresenter;

public class NavigationPresenter<V extends NavigationContract.View> extends BasePresenter<V> implements NavigationContract.Presenter {


    @Override
    public void onDriverStatisticClick() {
        view.navigateToStatisticScreen();
    }

    @Override
    public void onMyDeviceClick() {
        view.navigateToMyDeviceScreen();
    }

    @Override
    public void onDeviceClick() {
        view.navigateToDeviceScreen();
    }

    @Override
    public void onSettingButtonClick() {
        view.navigateToSettingScreen();
    }

    @Override
    public void onScheduleClick() {
        view.navigateToScheduleClick();
    }

    @Override
    public void onLoginClick() {
        view.navigateToLoginScreen();
    }
}
