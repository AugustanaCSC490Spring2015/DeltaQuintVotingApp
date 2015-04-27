package com.example.emilyleazer11.votingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by emilyleazer11 on 4/22/2015.
 */
public class NewCategoryActivity extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        Button endVotingSession = (Button) findViewById(R.id.endVotingSessionButton);
        endVotingSession.setOnClickListener(endSessionClickListener);

    }

    View.OnClickListener endSessionClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            launchMainActivity();
        }
    };

    public void launchMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
