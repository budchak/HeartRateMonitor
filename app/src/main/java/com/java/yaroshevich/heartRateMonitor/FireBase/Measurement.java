package com.java.yaroshevich.heartRateMonitor.FireBase;

public class Measurement {

    public long id;
    private String name;
    private int ticrate;
    private String data;


    public Measurement(long id, String name, int ticrate, String data) {
        this.id = id;
        this.name = name;
        this.ticrate = ticrate;
        this.data = data;
    }

    public Measurement(Measurement measurement){
        this.id = measurement.id;
        this.name = measurement.name;
        this.ticrate = measurement.ticrate;
        this.data = measurement.data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTicrate() {
        return ticrate;
    }

    public void setTicrate(int ticrate) {
        this.ticrate = ticrate;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

