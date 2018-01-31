package com.sissu;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView offerRecyclerView;
    private OfferAdapter adapter;
    private List<Offer> offerList;
    private static String url = "http://starlord.hackerearth.com/studio";
    View contentMain, contentAddInvoice, contentCurrentInvoice, contentPastInvoice, contentShare, contentFeedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent inent = new Intent(this, LoginActivity.class);

        // calling an activity using <intent-filter> action name
        //  Intent inent = new Intent("com.hmkcode.android.ANOTHER_ACTIVITY");

        //startActivity(inent);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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
        // Getting JSON from URL
        //  JSONObject json = getJSONFromUrl(url);

        offerRecyclerView = findViewById(R.id.offer_card_view);

        offerList = new ArrayList<>();
        adapter = new OfferAdapter(this, offerList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        offerRecyclerView.setLayoutManager(mLayoutManager);
        offerRecyclerView.setItemAnimator(new DefaultItemAnimator());
        offerRecyclerView.setNestedScrollingEnabled(false);
        offerRecyclerView.setAdapter(adapter);
        // prepareSongs();
      /*  try {
            prepareSongs(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
       */
        try {
            Glide.with(this).load(R.drawable.offers).into((ImageView) findViewById(R.id.offer_image));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            /*TextView tson=(TextView)findViewById(R.id.json);
            tson.setText(loadJSONFromAsset());
            Toast.makeText(this,loadJSONFromAsset(),Toast.LENGTH_SHORT).show();
            JSONObject jsnobject = new JSONObject(loadJSONFromAsset());
            */
            if(loadJSONFromAsset()==null)
            {}
            else{
            JSONObject jsonResponse = new JSONObject(loadJSONFromAsset());
            JSONArray cast = jsonResponse.getJSONArray("offers");
            for (int i=0; i<cast.length(); i++) {
                //Toast.makeText(this, "here", Toast.LENGTH_SHORT).show();
                JSONObject actor = cast.getJSONObject(i);
                String offer_value = actor.getString("offer_image");
                Offer offers = new Offer(offer_value);
                offerList.add(offers);
            }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getApplication().getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            Toast.makeText(this,"Stucked here...........",Toast.LENGTH_SHORT).show();
            return json;
        }
        return json;
    }
}
