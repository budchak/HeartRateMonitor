package com.java.yaroshevich.heartRateMonitor.FireBase.dao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.java.yaroshevich.heartRateMonitor.FireBase.Measurement;

public class MeasurementDao {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    public MeasurementDao(FirebaseDatabase firebaseDatabase, DatabaseReference databaseReference) {
        this.mDatabaseReference = databaseReference;
        this.mFirebaseDatabase = firebaseDatabase;

    }

    public void createMeasurement(Measurement measurement) {
        Measurement user = new Measurement(measurement);
        mDatabaseReference.child("user").child(Long.toString(user.id)).setValue(user);
    }


}
