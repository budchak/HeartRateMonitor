package com.java.yaroshevich.heartRateMonitor.device;

import com.java.yaroshevich.heartRateMonitor.device.Interface.DeviceInterface;

class CamDevice implements DeviceInterface {

    private String name;
    private String manufacturer;
    private String modelName;
    private String hardwareId;
    private String facing;


    public CamDevice(String name, String manufacturer, String modelName, String hardwareId, String facing) {

        this.name = name;
        this.manufacturer = manufacturer;
        this.modelName = modelName;
        this.hardwareId = hardwareId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getModelName() {
        return modelName;
    }

    @Override
    public String getManufacturer() {
        return manufacturer;
    }
}
