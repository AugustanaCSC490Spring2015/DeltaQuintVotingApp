package com.example.emilyleazer11.votingapp;

import android.app.Activity;
import android.os.Bundle;

import com.parse.ParseObject;

/**
 * Created by emilyleazer11 on 4/17/2015.
 */
public class AddCategoryActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_session);

        ParseObject testObject = new ParseObject("Sessions");
        testObject.put("session_name", "Test Session Name");
        testObject.saveInBackground();
    }
}
