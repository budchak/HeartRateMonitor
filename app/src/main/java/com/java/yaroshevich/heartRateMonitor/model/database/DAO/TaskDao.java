package com.java.yaroshevich.heartRateMonitor.model.database.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.java.yaroshevich.heartRateMonitor.model.Task;

import java.util.List;


@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    List<Task> getAll();

    @Query("SELECT * FROM Task WHERE id = :id")
    Task getById(long id);

    @Query("SELECT * FROM Task WHERE schedule_id = :id")
    List<Task> getByScheduleID(long id);

    @Insert
    void insert(Task employee);

    @Update
    void update(Task employee);

    @Delete
    void delete(Task employee);
}
