package com.example.fundraiser;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        System.out.println("onCreate\n");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // super.onCreate(savedInstanceState);



        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                byte[] buf = new byte[100500];
                Gson gson = new Gson();
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://javathon.kolebor.ru:8228/get_fundraisers");


// Request parameters and other properties.

                    try {
                        List<NameValuePair> params = new ArrayList<NameValuePair>(1);
                        params.add(new BasicNameValuePair("name", "maks"));
                        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

    //Execute and get the response.
                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();
                        System.out.println("---------------------------------------------------------------");
                        if (entity != null) {

                            try (InputStream instream = entity.getContent()) {
                                int len = instream.read(buf);
                                String json = new String(buf, 0, len, Charset.defaultCharset());
                                final String[] a = gson.fromJson(json, String[].class);
//                                System.out.println("dsjiadjisa: " + a);

                                Handler mainHandler = new Handler(Looper.getMainLooper());

                                Runnable myRunnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        setContentView(R.layout.app_bar_main);


                                        ListView lvMain = (ListView) findViewById(R.id.lvMain);

                                        // создаем адаптер
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                                                android.R.layout.simple_list_item_1, a);

                                        // присваиваем адаптер списку
                                        lvMain.setAdapter(adapter);
                                    } // This is your code
                                };
                                mainHandler.post(myRunnable);

                            }
                        }

                    } catch (ClientProtocolException e) {
                        System.out.println("client protocol exception");
                    } catch (IOException e) {
                        System.out.println("IO exeption");
                    }
                }
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void launchEventCreateActivity(View view) {

        Intent intent = new Intent(this, EventCreateActivity.class);
        startActivity(intent);
//
//        final int result = 1;
//        // To send data use putExtra with a String name followed by its value
//        intent.putExtra("callingActivity", "MainActivity");
//        startActivityForResult(intent, result);
    }

    public void updateFundRaiser(View view) {
        Intent intent = new Intent(this, EventCreateActivity.class);
        startActivity(intent);
    }
}