package com.java.yaroshevich.heartRateMonitor.UI.navigation;

import com.java.yaroshevich.heartRateMonitor.UI.base.BaseInterface;

public interface NavigationContract {


    interface View extends BaseInterface.View {
        void navigateToStatisticScreen();
        void navigateToMyDeviceScreen();
        void navigateToDeviceScreen();
        void navigateToSettingScreen();
        void navigateToScheduleClick();
        void navigateToLoginScreen();

    }

    interface Presenter{
        void onDriverStatisticClick();
        void onMyDeviceClick();
        void onDeviceClick();
        void onSettingButtonClick();
        void onScheduleClick();
        void onLoginClick();
    }
}
