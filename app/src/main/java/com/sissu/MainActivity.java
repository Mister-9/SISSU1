package com.sissu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    final int REQUEST_CODE = 100;
    final int PERMISSION_REQUEST = 200;
    Button scanbtn;
    TextView result;
    TextView DBresult;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mRootReference = firebaseDatabase.getReference();
    DatabaseReference mChildReference = mRootReference.child("message");
    View contentMain, contentAddInvoice, contentCurrentInvoice, contentPastInvoice, contentShare, contentFeedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        scanBarcode();
        Intent inent = new Intent(this, LoginActivity.class);

        int request_code=109;
        startActivityForResult(inent,request_code);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            initViews();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void initViews() throws JSONException {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.offer_card_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<Offer> offer = prepareData();
        OfferAdapter adapter = new OfferAdapter(getApplicationContext(),offer);
        recyclerView.setAdapter(adapter);
    }
    private ArrayList<Offer> prepareData() throws JSONException {

        ArrayList<Offer> offerList = new ArrayList<>();
        if(loadJSONFromAsset()==null)
        {}
        else {
            JSONObject jsonResponse = new JSONObject(loadJSONFromAsset());
            JSONArray cast = jsonResponse.getJSONArray("offers");
            for (int i = 0; i < cast.length(); i++) {
                //Toast.makeText(this, "here", Toast.LENGTH_SHORT).show();
                JSONObject actor = cast.getJSONObject(i);
                String offer_value = actor.getString("offer_image");
                Offer offers = new Offer(offer_value);
                offerList.add(offers);
            }
        }
        return offerList;
    }
    private void initToolbar(){
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
    public void scanBarcode(){
        scanbtn = (Button) findViewById(R.id.scanbtn);
        result = (TextView) findViewById(R.id.result);
        DBresult = (TextView) findViewById(R.id.dbresult);
        DBresult.setText("output of barcode");
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            if (data != null)
            {
                final Barcode barcode = data.getParcelableExtra("barcode");
                result.post(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(barcode.displayValue);
                    }
                });
            }
        }
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("returnKey1")) {
                Toast.makeText(this, data.getExtras().getString("returnKey1"),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mChildReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message = dataSnapshot.getValue(String.class);
                DBresult.setText(message);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                DBresult.setText("Hey");
            }
        });

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
            toolbar.setTitle("Offers");
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
            toolbar.setTitle("New Invoice");
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
