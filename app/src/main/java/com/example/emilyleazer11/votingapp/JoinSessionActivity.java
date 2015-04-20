package com.example.emilyleazer11.votingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by MHanson on 4/17/2015.
 */
public class JoinSessionActivity extends Activity {


    public static final String TAG = "Voting App Activity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_session);

        Button joinSession = (Button) findViewById(R.id.joinSessionButton);
        joinSession.setOnClickListener(createClickListener);

    }

    View.OnClickListener createClickListener = new View.OnClickListener() {
        @Override
            public void onClick(View v){
                joinSession();
            }
        };

    public void joinSession(){
         Intent intent = new Intent (this, SessionActivity.class);
         startActivity(intent);
    }


    }



