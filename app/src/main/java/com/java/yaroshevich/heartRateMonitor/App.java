package com.java.yaroshevich.heartRateMonitor;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.java.yaroshevich.heartRateMonitor.FireBase.FirebaseAPI;

import com.java.yaroshevich.heartRateMonitor.model.database.DatabaseHelper;
import com.java.yaroshevich.heartRateMonitor.repository.MeasurementRepository;

public class App extends Application {

    private DatabaseHelper db;

    private FirebaseAPI firebaseAPI = new FirebaseAPI();

    private FirebaseUser user;


    private MeasurementRepository repository;
    //private measurmentRepository;
    //private custonTraineRepository;
    //private allarmRepository;
    //private

    public static App instance;

    @Override
    public void onCreate() {
        super.onCreate();

        db =  Room.databaseBuilder(getApplicationContext(),
                DatabaseHelper.class, "database").build();
        repository = new MeasurementRepository(getApplicationContext());


    }

    public FirebaseAPI getFirebaseApi(){

        firebaseAPI.initFirebase(this);

        return firebaseAPI;
    }

    public MeasurementRepository getRepository() {
        return repository;
    }

    public void setRepository(MeasurementRepository repository) {
        this.repository = repository;
    }

    public static App getInstance(){
        return instance;
    }

    public DatabaseHelper getDatabase() {
        if(db == null){
            db =  Room.databaseBuilder(getApplicationContext(),
                    DatabaseHelper.class, "database").build();
        }
        return db;
    }

    public FirebaseUser getUser(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if(user == null){
            //startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }else{
            return user;
        }
        return user;
    }

    public void setUser(FirebaseUser user){
        this.user = user;
    }

}
