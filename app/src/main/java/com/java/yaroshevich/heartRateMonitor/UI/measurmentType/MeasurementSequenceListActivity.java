package com.java.yaroshevich.heartRateMonitor.UI.measurmentType;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.java.yaroshevich.heartRateMonitor.R;
import com.java.yaroshevich.heartRateMonitor.UI.camera.CameraActivity;
import com.java.yaroshevich.heartRateMonitor.UI.camera.RuntimeMeasurementActivity;
import com.java.yaroshevich.heartRateMonitor.UI.navigation.NavigationDrawerActivity;

import java.util.List;

public class MeasurementSequenceListActivity extends NavigationDrawerActivity implements IMeasurementType.View, RecyclerViewItemClickListener {

    public static final String MEASUREMENT_TYPE = "MEASUREMENT_NAME";
    private MeasurementTypePresenter presenter;
    private RecyclerView recyclerView;
    private MeasurementTypeRecyclerViewAdapter adapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_measurement_type);

        getSupportActionBar().setTitle("Измерения");
        presenter = new MeasurementTypePresenter();

        recyclerView = findViewById(R.id.list_measurement_type_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MeasurementTypeRecyclerViewAdapter(this);
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);
        recyclerView.setAdapter(adapter);

        presenter.onAttach(this);

    }


    @Override
    public void navigateTo() {

    }

    @Override
    public void showTypes(List<String> types) {
        adapter.setList(types);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void navigateToWithData(int viewId, String data) {
        Intent intent;
        if (viewId == 0) {
        intent = new Intent(this, CameraActivity.class);
        intent.putExtra(MEASUREMENT_TYPE, data);

        } else {
            intent = new Intent(this, RuntimeMeasurementActivity.class);
            intent.putExtra(MEASUREMENT_TYPE, data);
        }
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView = findViewById(R.id.list_measurement_type_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MeasurementTypeRecyclerViewAdapter(this);
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);
        presenter.onAttach(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int id) {
        presenter.onItemClick(id);
    }
}
