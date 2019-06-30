package com.java.yaroshevich.heartRateMonitor.UI.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.java.yaroshevich.heartRateMonitor.App;
import com.java.yaroshevich.heartRateMonitor.R;
import com.java.yaroshevich.heartRateMonitor.UI.navigation.Navigation;
import com.java.yaroshevich.heartRateMonitor.UI.navigation.NavigationDrawerActivity;
import com.java.yaroshevich.heartRateMonitor.model.Measurement;
import com.java.yaroshevich.heartRateMonitor.model.database.DatabaseHelper;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MainActivity extends NavigationDrawerActivity implements MainInterface.View {

    private MainPresenter presenter;

    private Button mRunButton;
    private List<Measurement> measurements;
    public static Context context;
    private RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;
    private viewResultRecyclerViewAdapter viewResultRecyclerViewAdapter;
    private BarChart barChart;
    private Spinner spinner;
    private int possitionSelector;
    private String[] strings = {"времени", "типу"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Статистика");
        presenter = new MainPresenter(((App) getApplicationContext()).getRepository());

        context = this;
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        //itemDecor.setDrawable(getResources().getDrawable(R.drawable.divider_main_color));
        recyclerView.addItemDecoration(itemDecor);
        barChart = findViewById(R.id.barChart);

        spinner = findViewById(R.id.mainSpinner);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strings);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                possitionSelector = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                possitionSelector = 0;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        presenter.onAttach(this);
        presenter.onReady();


    }

    @Override
    public void updateStatistic(List<Measurement> measurements) {
        this.measurements = measurements;
        //Date date = stringToDate(measurements.get(0).getDate(), "EEE MMM d HH:mm:ss zz yyyy");
        viewResultRecyclerViewAdapter = new viewResultRecyclerViewAdapter(this, measurements);
        recyclerView.setAdapter(viewResultRecyclerViewAdapter);
    }

    private boolean isPackageExpired(String date) {
        boolean isExpired = false;
        Date expiredDate = stringToDate(date, "EEE MMM d HH:mm:ss zz yyyy");
        if (new Date().after(expiredDate))
            isExpired = true;
        return isExpired;
    }

    private Date stringToDate(String aDate, String aFormat) {
        if (aDate == null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;
    }

    @Override
    public void startNewActivity(int activityID) {
        Navigation.navigateToCameraScreen(this);
    }

    @Override
    public void showMassage() {

    }
}