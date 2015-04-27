package com.example.emilyleazer11.votingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by MHanson on 4/20/2015.
 */
public class SessionActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(submitClickListener);
    }

    View.OnClickListener submitClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            submitVote();
        }
    };

    public void submitVote(){
        Intent intent = new Intent(this, EndSessionActivity.class);
        startActivity(intent);
    }





}
