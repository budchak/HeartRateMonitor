package com.java.yaroshevich.heartRateMonitor.UI.main;

import android.os.AsyncTask;
import android.util.Log;

import com.java.yaroshevich.heartRateMonitor.App;
import com.java.yaroshevich.heartRateMonitor.UI.base.BasePresenter;
import com.java.yaroshevich.heartRateMonitor.model.Measurement;
import com.java.yaroshevich.heartRateMonitor.model.database.DatabaseHelper;
import com.java.yaroshevich.heartRateMonitor.repository.MeasurementRepository;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter extends BasePresenter<MainInterface.View> implements MainInterface.Presenter {


    MeasurementRepository repository;

    public MainPresenter(MeasurementRepository repository){
        this.repository = repository;
    }


    @Override
    public void onButtonClick() {
        view.startNewActivity(1);
    }

    @Override
    public void onAttach(MainInterface.View view) {
        super.onAttach(view);
    }

    @Override
    public void onReady(){
        new MeasurementAsyncTask(repository, view).execute();
    }


    private class MeasurementAsyncTask extends AsyncTask<String, Void, List<Measurement>> {

        MeasurementRepository repository;
        MainInterface.View view;

        public MeasurementAsyncTask(MeasurementRepository repository, MainInterface.View view) {
            this.repository = repository;
            this.view = view;
        }


        @Override
        protected List<Measurement> doInBackground ( final String...params){

            return repository.findAll();
        }

        @Override
        protected void onPostExecute (List < Measurement > result) {
            view.updateStatistic(result);
        }
    }
}

