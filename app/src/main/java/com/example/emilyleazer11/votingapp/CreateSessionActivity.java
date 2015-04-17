package com.example.emilyleazer11.votingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by emilyleazer11 on 4/17/2015.
 */
public class CreateSessionActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);

        Button nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(nextClickListener);

    }

    View.OnClickListener nextClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            nextPage();
        }
    };

    public void nextPage(){
        Intent intent = new Intent(this, AddCategoryActivity.class);
        startActivity(intent);


    }



}
