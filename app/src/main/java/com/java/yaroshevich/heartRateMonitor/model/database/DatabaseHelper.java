package com.java.yaroshevich.heartRateMonitor.model.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.java.yaroshevich.heartRateMonitor.model.Measurement;

import com.java.yaroshevich.heartRateMonitor.model.Task;
import com.java.yaroshevich.heartRateMonitor.model.database.DAO.MeasurementDao;


import com.java.yaroshevich.heartRateMonitor.model.database.DAO.TaskDao;

@Database(entities = {Measurement.class, Task.class}, version = 2, exportSchema = false)
public abstract class DatabaseHelper extends RoomDatabase {

    public abstract MeasurementDao getMeasurementDao();
    public abstract TaskDao getTaskDao();


}