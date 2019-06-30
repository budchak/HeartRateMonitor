package com.java.yaroshevich.heartRateMonitor.FireBase;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.java.yaroshevich.heartRateMonitor.FireBase.dao.MeasurementDao;

public class FirebaseAPI {

    static FirebaseDatabase mFirebaseDatabase;
    static DatabaseReference mDatabaseReference;

    private MeasurementDao measurementDao;

    public static void initFirebase(Context context) {

        FirebaseApp.initializeApp(context);
        //получаем точку входа для базы данных
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        //получаем ссылку для работы с базой данных
        mDatabaseReference = mFirebaseDatabase.getReference();
    }

    public MeasurementDao getMeasurementDao(){
        if(measurementDao == null){
            measurementDao = new MeasurementDao(mFirebaseDatabase, mDatabaseReference);
        }
        return measurementDao;
    }







}
