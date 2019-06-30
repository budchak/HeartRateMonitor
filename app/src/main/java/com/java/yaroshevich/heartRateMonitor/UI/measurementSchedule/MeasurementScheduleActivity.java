package com.java.yaroshevich.heartRateMonitor.UI.measurementSchedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.java.yaroshevich.heartRateMonitor.UI.navigation.Navigation;
import com.java.yaroshevich.heartRateMonitor.model.Measurement;
import com.java.yaroshevich.heartRateMonitor.R;
import com.java.yaroshevich.heartRateMonitor.UI.base.BaseActivity;
import com.java.yaroshevich.heartRateMonitor.UI.measurementSchedule.Interface.NewMeasurmentCreateListener;
import com.java.yaroshevich.heartRateMonitor.UI.measurementSchedule.Interface.ScheduleContract;
import com.java.yaroshevich.heartRateMonitor.UI.measurementSchedule.Interface.ViewHolderEventListener;
import com.java.yaroshevich.heartRateMonitor.UI.camera.saveResult.ResultSaverFragment;

import java.util.ArrayList;
import java.util.List;

public class MeasurementScheduleActivity extends BaseActivity implements ScheduleContract.View
                        , NewMeasurmentCreateListener
                        , ViewHolderEventListener
                        , CreateDialogCallback{

    private RecyclerView recyclerView;
    private ScheduleRecyclerViewAdapter adapter;
    private ScheduleContract.Presenter presenter;

    private ImageButton cancel, apply;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement_shedule);

        setTitle(getResources().getString(R.string.new_measurement));

        presenter = new MeasurementSchedulePresenter(this);

        cancel = findViewById(R.id.cancelImageButton);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.navigateToMainScreen(v.getContext());
            }
        });

        apply = findViewById(R.id.ApplyImageButton);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.navigateToMainScreen(v.getContext());
            }
        });

        recyclerView = findViewById(R.id.schedule_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration itemDecor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);

        adapter = new ScheduleRecyclerViewAdapter(new ArrayList<Measurement>());
        adapter.setCreateListener(this);
        adapter.setEventListener(this);

        recyclerView.setAdapter(adapter);


    }


    @Override
    public void showListMeasurement(List<Measurement> measurement) {
        adapter.setData(measurement);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showCreateView() {
        NewMeasurementFragment fragment = new NewMeasurementFragment();
        fragment.setDialogCallback(this);
        fragment.show(getSupportFragmentManager(),"выбор");
    }

    @Override
    public void showUpdateView() {
        Fragment fragment = new ResultSaverFragment();
        ((ResultSaverFragment) fragment).show(getSupportFragmentManager(),"выбор");
    }

    @Override
    public void showRemoveView() {
        Fragment fragment = new ResultSaverFragment();
        ((ResultSaverFragment) fragment).show(getSupportFragmentManager(),"выбор");
    }

    @Override
    public void addNewMeasurementType() {
        presenter.onAddNewElementClick();
    }

    @Override
    public void updateElement(int id) {
        presenter.onUpdateElementClick(id);
    }

    @Override
    public void RemoveElement(int id) {
        presenter.onRemoveElementClick(id);
    }


    @Override
    public void onSuccess(Measurement measurement) {
        presenter.updateSchedule(measurement);
    }

    @Override
    public void onFailed(String error) {

    }
}
