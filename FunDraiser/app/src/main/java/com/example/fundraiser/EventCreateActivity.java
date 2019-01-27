package com.example.fundraiser;

import com.example.fundraiser.connectWithServer.CreateFundRaiser;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class EventCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, PARTICIPANTS);
        MultiAutoCompleteTextView textView = findViewById(R.id.participants_list);
        textView.setAdapter(adapter);
        textView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }

    private static final String[] PARTICIPANTS = new String[] {
            "Ромалма", "Лапник", "Димон", "Бучневич", "Димасик", "Илюха", "Макс", "Некит"
    };



    public void onSendUsersName(View view) throws Exception {

        // Get the users name from the EditText

        EditText fundRaiser = (EditText) findViewById(R.id.event_name);
        EditText participants = (EditText) findViewById(R.id.participants_list);

        String fundRaiserStr = String.valueOf(fundRaiser.getText());
        String participantsStr = String.valueOf(participants.getText());

//        System.out.println("-------------------------------------------------------------------------------------");
//
//        System.out.println(fundRaiserStr);
//        System.out.println(participantsStr);
//        System.out.println(json);
//        System.out.println("-------------------------------------------------------------------------------------");
        // Define our intention to go back to ActivityMain
        Gson gson = new Gson();
        CreateFundRaiser fundraiserForJson = new CreateFundRaiser(fundRaiserStr, participantsStr);
        final String json = gson.toJson(fundraiserForJson);
        final byte[] jsoner = json.getBytes("utf-8");


        Intent goingBack = new Intent();
        // Define the String name and the value to assign to it
        // Sends data back to the parent and can use RESULT_CANCELED, RESULT_OK, or any
        // custom values starting at RESULT_FIRST_USER. RESULT_CANCELED is sent if
        // this Activity crashes
        setResult(RESULT_OK, goingBack);
        // Close this Activity
        finish();

        Thread thr = new Thread() {
            @Override
            public void run() {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://javathon.kolebor.ru:8228/create_fundraising");

                try {
                    httppost.setEntity(new ByteArrayEntity(jsoner));
                    HttpResponse response = httpclient.execute(httppost);

                } catch (ClientProtocolException e) {
                    System.out.println("client protocol exception");
                } catch (IOException e) {
                    System.out.println("IO exeption");
                }
            }
        };

        thr.start();
        thr.join();
    }


}