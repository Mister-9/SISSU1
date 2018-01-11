package com.sissu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    View contentMain, contentAddInvoice, contentCurrentInvoice, contentPastInvoice, contentShare, contentFeedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentMain = findViewById(R.id.app_bar_main);
        contentAddInvoice = findViewById(R.id.app_bar_addInvoice);
        contentCurrentInvoice = findViewById(R.id.app_bar_currentInvoice);
        contentPastInvoice = findViewById(R.id.app_bar_pastInvoice);
        contentShare = findViewById(R.id.app_bar_share);
        contentFeedback = findViewById(R.id.app_bar_feedback);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
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
        getMenuInflater().inflate(R.menu.main, menu);
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
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_offer) {
            //camera_activity
            contentAddInvoice.setVisibility(View.GONE);
            contentCurrentInvoice.setVisibility(View.GONE);
            contentPastInvoice.setVisibility(View.GONE);
            contentShare.setVisibility(View.GONE);
            contentFeedback.setVisibility(View.GONE);
            contentMain.setVisibility(View.VISIBLE);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
            toolbar.setTitle("Smart Invoice");
            //toolbar control
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        } else if (id == R.id.nav_addInvoice) {
            //alert activity
            contentFeedback.setVisibility(View.GONE);
            contentCurrentInvoice.setVisibility(View.GONE);
            contentShare.setVisibility(View.GONE);
            contentPastInvoice.setVisibility(View.GONE);
            contentMain.setVisibility(View.GONE);
            contentAddInvoice.setVisibility(View.VISIBLE);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_addInvoice);
            toolbar.setTitle("Add New Invoice");
            //toolbar control
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        } else if (id == R.id.nav_currentInvoioce) {
            //contact_activity
            contentFeedback.setVisibility(View.GONE);
            contentMain.setVisibility(View.GONE);
            contentAddInvoice.setVisibility(View.GONE);
            contentPastInvoice.setVisibility(View.GONE);
            contentShare.setVisibility(View.GONE);
            contentCurrentInvoice.setVisibility(View.VISIBLE);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_currentInvoice);
            toolbar.setTitle("Current Invoice");
            //toolbar control
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);


        } else if (id == R.id.nav_pastInvoice) {
            //fackcall activity
            contentFeedback.setVisibility(View.GONE);
            contentMain.setVisibility(View.GONE);
            contentCurrentInvoice.setVisibility(View.GONE);
            contentShare.setVisibility(View.GONE);
            contentAddInvoice.setVisibility(View.GONE);
            contentPastInvoice.setVisibility(View.VISIBLE);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_pastInvoice);
            toolbar.setTitle("Past Invoices");
            //toolbar control
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);


        } else if (id == R.id.nav_share) {
            //location_activity
            contentFeedback.setVisibility(View.GONE);
            contentMain.setVisibility(View.GONE);
            contentAddInvoice.setVisibility(View.GONE);
            contentPastInvoice.setVisibility(View.GONE);
            contentCurrentInvoice.setVisibility(View.GONE);
            contentShare.setVisibility(View.VISIBLE);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_share);
            toolbar.setTitle("Share");
            //toolbar control
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

        } else if (id == R.id.nav_feedback) {
            //setting_activity
            contentMain.setVisibility(View.GONE);
            contentAddInvoice.setVisibility(View.GONE);
            contentCurrentInvoice.setVisibility(View.GONE);
            contentShare.setVisibility(View.GONE);
            contentPastInvoice.setVisibility(View.GONE);
            contentFeedback.setVisibility(View.VISIBLE);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_feedback);
            toolbar.setTitle("Feedback");
            //toolbar control
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
