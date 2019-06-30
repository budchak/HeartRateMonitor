package com.java.yaroshevich.heartRateMonitor.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity
public class Measurement {

    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo(name = "task_id")
    private int taskID;
    private String name;
    private String type;
    private int ticrate;
    private String date;
    private String status_id;

    public Measurement(long id, int taskID, String name, int ticrate, String date, String status_id, String type) {
        this.id = id;
        this.taskID = taskID;
        this.name = name;
        this.ticrate = ticrate;
        this.date = date;
        this.status_id = status_id;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public Measurement(String name, int ticrate, String data) {
        this.name = name;
        this.ticrate = ticrate;
        this.date = data;
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

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
