package com.java.yaroshevich.heartRateMonitor.model.database.DAO;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.java.yaroshevich.heartRateMonitor.model.Measurement;

import java.util.List;

@Dao
public interface MeasurementDao {

    @Query("SELECT * FROM measurement")
    List<Measurement> getAll();

    @Query("SELECT * FROM measurement WHERE id = :id")
    Measurement getById(long id);

    @Query("SELECT * FROM measurement WHERE task_id = :id")
    Measurement getByTaskId(long id);

    @Insert
    void insert(Measurement employee);

    @Update
    void update(Measurement employee);

    @Delete
    void delete(Measurement employee);
}
