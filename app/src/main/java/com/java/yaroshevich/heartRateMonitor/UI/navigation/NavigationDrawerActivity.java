package com.java.yaroshevich.heartRateMonitor.UI.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.java.yaroshevich.heartRateMonitor.R;
import com.java.yaroshevich.heartRateMonitor.UI.base.BaseActivity;
import com.java.yaroshevich.heartRateMonitor.UI.camera.CameraActivity;

public class NavigationDrawerActivity extends BaseActivity implements NavigationContract.View, NavigationView.OnNavigationItemSelectedListener {

    NavigationContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_navigation_draver);
        presenter = new NavigationPresenter<>();
        ((NavigationPresenter) presenter).onAttach(this);


    }

    @Override
    public void setContentView(int layoutResID) {

        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_navigation_draver, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.frameLayout);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_draver, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            presenter.onMyDeviceClick();
        } else if (id == R.id.nav_gallery) {
            presenter.onDriverStatisticClick();
        } else if (id == R.id.nav_setting) {
            presenter.onSettingButtonClick();
        } else if (id == R.id.nav_manage) {
            presenter.onLoginClick();
        }else if (id == R.id.nav_schedule) {
            presenter.onScheduleClick();
        }else if (id == R.id.nav_device){
            presenter.onDeviceClick();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void navigateToStatisticScreen() {
        Navigation.navigateToStatisticScreen(this);
    }

    @Override
    public void navigateToMyDeviceScreen() {
        Intent i=new Intent(NavigationDrawerActivity.this, CameraActivity.class);
        startActivity(i);
    }

    @Override
    public void navigateToDeviceScreen() {
        Navigation.navigateToDeviceScreen(this);
    }

    @Override
    public void navigateToSettingScreen() {
        Navigation.navigateToSettingScreen(this);
    }

    @Override
    public void navigateToScheduleClick() {
        Navigation.navigateToScheduleScreen(this);
    }

    @Override
    public void navigateToLoginScreen() {
        Navigation.navigateToLoginScreen(this);
    }

    @Override
    public void showMassage() {

    }
}
