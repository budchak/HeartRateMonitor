package com.java.yaroshevich.heartRateMonitor.UI.devicesList;

import com.java.yaroshevich.heartRateMonitor.UI.base.BaseInterface;
import com.java.yaroshevich.heartRateMonitor.device.Interface.DeviceInterface;

import java.util.List;

public interface ListActivityCantract {

    interface View extends BaseInterface.View {
       void showDeviceList(List<DeviceInterface> devicesList);
       void updateDeviceList(DeviceInterface device);
       void showLoading();
       void hideLoading();
    }
}
