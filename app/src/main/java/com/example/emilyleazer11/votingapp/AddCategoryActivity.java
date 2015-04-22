package com.example.emilyleazer11.votingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parse.ParseObject;

/**
 * Created by emilyleazer11 on 4/17/2015.
 */
public class AddCategoryActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        Button quitButton = (Button) findViewById(R.id.quitButton);
        quitButton.setOnClickListener(quitClickListener);
    }


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
}
