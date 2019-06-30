package com.java.yaroshevich.heartRateMonitor.UI.devicesList;

import com.java.yaroshevich.heartRateMonitor.UI.base.BaseInterface;

public class DeviceListPresenter implements BaseInterface.Presenter {

    ListActivityCantract.View view;


    void onButtonClick() {
       /** DeviceAPI.getDeviceScaner().start(new ScannerCallbackInterface() {
            @Override
            public void onDeviceFound(DeviceInterface device) {
                List<DeviceInterface> deviceList = DeviceAPI.getListDevice();

                if(!deviceList.contains(device)){
                    deviceList.add(device);
                }

                view.showDeviceList(deviceList);
            }
        }, DeviceType.CAM);
        **/
    }

    @Override
    public void onAttach(BaseInterface.View mvpView) {
        this.view = (ListActivityCantract.View) mvpView;
    }

    @Override
    public void onDetach() {
        view = null;
    }

}
