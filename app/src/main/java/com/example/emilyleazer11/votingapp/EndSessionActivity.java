package com.example.emilyleazer11.votingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by emilyleazer11 on 4/24/2015.
 */
public class EndSessionActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_session);

        Button quitButton = (Button) findViewById(R.id.quitSessionButton);
        quitButton.setOnClickListener(quitClickListener);

        Button joinAnotherSessionButton = (Button) findViewById(R.id.joinAnotherSessionButton);
        joinAnotherSessionButton.setOnClickListener(joinAnotherSessionButtonClickListener);

    }

    // returns the user back to the home screen
    View.OnClickListener quitClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            launchMainActivity();
        }
    };
    public void launchMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // reopens the login screen for joing the session
    View.OnClickListener joinAnotherSessionButtonClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            launchJoinSessionActivity();
        }
    };
    public void launchJoinSessionActivity(){
        Intent intent = new Intent(this, JoinSessionActivity.class);
        startActivity(intent);
    }


}
