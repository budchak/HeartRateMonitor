package com.java.yaroshevich.heartRateMonitor.repository;

import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.java.yaroshevich.heartRateMonitor.model.AppSchedule;
import com.java.yaroshevich.heartRateMonitor.model.AppTask;
import com.java.yaroshevich.heartRateMonitor.model.Measurement;
import com.java.yaroshevich.heartRateMonitor.model.Schedule;
import com.java.yaroshevich.heartRateMonitor.model.Task;
import com.java.yaroshevich.heartRateMonitor.model.database.DAO.MeasurementDao;

import com.java.yaroshevich.heartRateMonitor.model.database.DAO.TaskDao;
import com.java.yaroshevich.heartRateMonitor.model.database.DatabaseHelper;

import java.util.List;

public class ScheduleRepository implements Repository<Schedule> {


    private Context context;
    private DatabaseHelper db;
    private MeasurementDao measurementDao;
    private TaskDao taskDao;
    private AppSchedule schedule;
    private MutableLiveData<List<Measurement>> liveData = new MutableLiveData<>();

    public ScheduleRepository(Context context) {
        this.context = context;
        db = Room.databaseBuilder(context,
                DatabaseHelper.class, "database").build();

        measurementDao = db.getMeasurementDao();
        taskDao = db.getTaskDao();
    }

    @Override
    public Schedule find(long id) {

        List<Task> tasks = taskDao.getByScheduleID(id);
        for (Task task:tasks){

        }
        return null;
    }

    @Override
    public List<Schedule> findAll() {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void insert(Schedule schedule) {

    }

    @Override
    public void update(Schedule schedule) {

    }
}
