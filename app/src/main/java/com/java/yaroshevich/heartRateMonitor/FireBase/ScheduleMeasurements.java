package com.java.yaroshevich.heartRateMonitor.FireBase;

public class ScheduleMeasurements {

    private int id;
    private String name;
    private int measurement_id;

    public ScheduleMeasurements(int id, String name, int measurement_id) {
        this.id = id;
        this.name = name;
        this.measurement_id = measurement_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMeasurement_id() {
        return measurement_id;
    }

    public void setMeasurement_id(int measurement_id) {
        this.measurement_id = measurement_id;
    }
}
