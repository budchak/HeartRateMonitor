package com.java.yaroshevich.heartRateMonitor.model.database.DAO;


/**@Dao
public interface ScheduleDao {
    @Query("SELECT * FROM Schedule")
    List<Schedule> getAll();

    @Query("SELECT * FROM Schedule WHERE id = :id")
    Schedule getById(long id);

    @Insert
    void insert(Schedule employee);

    @Update
    void update(Schedule employee);

    @Delete
    void delete(Schedule employee);
}**/
