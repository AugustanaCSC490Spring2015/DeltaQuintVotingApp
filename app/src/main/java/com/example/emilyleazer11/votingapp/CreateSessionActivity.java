package com.example.emilyleazer11.votingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseObject;

/**
 * Created by emilyleazer11 on 4/17/2015.
 */
public class CreateSessionActivity extends Activity {

    public String newSessionName;

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);

        Button nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(nextClickListener);

        Button quitButton = (Button) findViewById(R.id.quitButton);
        quitButton.setOnClickListener(quitClickListener);
    }

    View.OnClickListener nextClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            nextPage();
        }
    };

    View.OnClickListener quitClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            launchMainActivity();
        }
    };

    public void nextPage(){

        createCategory();

        Intent intent = new Intent(this, NewCategoryActivity.class);
        startActivity(intent);
    }

    public void launchMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void createCategory() {

        EditText sessionNameText = (EditText) findViewById(R.id.sessionNameEditText);
        newSessionName= sessionNameText.getText().toString();

        ParseObject newSession = new ParseObject("Sessions");
        newSession.put("session_name", newSessionName);
        newSession.saveInBackground();
    }
}
