package com.java.yaroshevich.heartRateMonitor.UI.navigation;

import android.content.Context;
import android.content.Intent;

import com.java.yaroshevich.heartRateMonitor.UI.camera.CameraActivity;
import com.java.yaroshevich.heartRateMonitor.UI.camera.RangeMeasurementActivity;
import com.java.yaroshevich.heartRateMonitor.UI.main.MainActivity;
import com.java.yaroshevich.heartRateMonitor.UI.measurementSchedule.MeasurementScheduleActivity;
import com.java.yaroshevich.heartRateMonitor.UI.measurmentType.MeasurementSequenceListActivity;
import com.java.yaroshevich.heartRateMonitor.UI.setting.SettingsActivity;
import com.java.yaroshevich.heartRateMonitor.UI.signin.LoginActivity;
import com.java.yaroshevich.heartRateMonitor.UI.signin.RegisterActivity;

public class Navigation {


    public static void navigateToMainScreen(Context context){
        context.startActivity(new Intent(context, MeasurementSequenceListActivity.class));
    }

    public static void navigateToRegisterScreen(Context context){
        context.startActivity(new Intent(context, RegisterActivity.class));
    }

    public static void navigateToLoginScreen(Context context){
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public static void navigateToCameraScreen(Context context){
        context.startActivity(new Intent(context, CameraActivity.class));
    }

    public static void navigateToSettingScreen(Context context){
        context.startActivity(new Intent(context, SettingsActivity.class));
    }

    public static void navigateToMSScreen(Context context){
        context.startActivity(new Intent(context, CameraActivity.class));
    }

    public static void navigateToScheduleScreen(Context context){
        context.startActivity(new Intent(context, MeasurementScheduleActivity.class));
    }

    public static void navigateToStatisticScreen(Context context){
        context.startActivity(new Intent(context, MainActivity.class));
    }


    public static void navigateToDeviceScreen(Context context) {
        context.startActivity(new Intent(context, RangeMeasurementActivity.class));
    }
}
