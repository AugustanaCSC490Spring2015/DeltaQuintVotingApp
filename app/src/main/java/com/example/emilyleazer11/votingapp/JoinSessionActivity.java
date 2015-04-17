package com.example.emilyleazer11.votingapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by MHanson on 4/17/2015.
 */
public class JoinSessionActivity extends Activity {


    public static final String TAG = "Voting App Activity";

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_session);

        //doing nothing
        Log.w(TAG, "test");


    }
}


