package com.java.yaroshevich.heartRateMonitor.repository;

import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.java.yaroshevich.heartRateMonitor.model.Measurement;

import com.java.yaroshevich.heartRateMonitor.model.database.DAO.MeasurementDao;
import com.java.yaroshevich.heartRateMonitor.model.database.DatabaseHelper;

import java.util.List;

public class MeasurementRepository implements Repository<Measurement> {

    private Context context;
    private DatabaseHelper db;
    private MeasurementDao measurementDao;
    private MutableLiveData<List<Measurement>> liveData = new MutableLiveData<>();

    public MeasurementRepository(Context context) {
        this.context = context;
        db = Room.databaseBuilder(context,
                DatabaseHelper.class, "database").build();
        measurementDao = db.getMeasurementDao();
    }


    @Override
    public Measurement find(long id) {
        return null;
    }

    @Override
    public List<Measurement> findAll() {
        return measurementDao.getAll();

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void insert(final Measurement measurement) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                measurementDao.insert(measurement);

            }
        }).start();
    }

    @Override
    public void update(final Measurement measurement) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                measurementDao.update(measurement);
            }
        }).start();
    }

}
