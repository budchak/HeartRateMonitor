package com.java.yaroshevich.heartRateMonitor.model;

public class AppTask {

    private String id_schedule;
    private String id_measurement;
    private String number;
    private boolean status;
    private int scheduleID;

    private Measurement measurement;

    public AppTask(String id_schedule, String id_measurement, String number, boolean status, int scheduleID) {

        this.id_schedule = id_schedule;
        this.id_measurement = id_measurement;
        this.number = number;
        this.status = status;
        this.scheduleID = scheduleID;
    }

    public String getId_schedule() {
        return id_schedule;
    }

    public void setId_schedule(String id_schedule) {
        this.id_schedule = id_schedule;
    }

    public String getId_measurement() {
        return id_measurement;
    }

    public void setId_measurement(String id_measurement) {
        this.id_measurement = id_measurement;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }
}


