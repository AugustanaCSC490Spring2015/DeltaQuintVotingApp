package com.example.emilyleazer11.votingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;

/**
 * Created by emilyleazer11 on 4/17/2015.
 */
public class CreateSessionActivity extends Activity {

    public static final String TAG = "VotingApp Activity";
    public String newSessionName;
    public String newSessionPassword;
    public String newConfirmSessionPassword;
    public String newAdminPassword;
    public String newConfirmAdminPassword;
    public Boolean sessionPasswordEqual;
    public Boolean adminPasswordEqual;

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
             createCategory();
        }
    };

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

    public void createCategory() {

        EditText sessionNameText = (EditText) findViewById(R.id.sessionNameEditText);
        newSessionName= sessionNameText.getText().toString();

        EditText sessionPasswordText = (EditText) findViewById(R.id.sessionPasswordEditText);
        newSessionPassword = sessionPasswordText.getText().toString();

        EditText sessionConfirmPasswordText = (EditText) findViewById(R.id.confirmPasswordEditText);
        newConfirmSessionPassword = sessionConfirmPasswordText.getText().toString();

        EditText adminPasswordText = (EditText) findViewById(R.id.createAdminPassword);
        newAdminPassword = adminPasswordText.getText().toString();

        EditText adminConfirmPasswordText = (EditText) findViewById(R.id.confirmAdminPassword);
        newConfirmAdminPassword = adminConfirmPasswordText.getText().toString();

        Log.w(TAG, "Session Name: " + newSessionName);
        Log.w(TAG, "Session PW: " + newSessionPassword);
        Log.w(TAG, "Confirm Session PW: " + newConfirmSessionPassword);
        Log.w(TAG, "Admin PW: " + newAdminPassword);
        Log.w(TAG, "Confirm Admin PW: " + newConfirmAdminPassword);

        sessionPasswordEqual = newSessionPassword.equals(newConfirmSessionPassword);
        adminPasswordEqual = newAdminPassword.equals(newConfirmAdminPassword);

        Log.w(TAG, "Are Session Passwords Equal? " + sessionPasswordEqual);
        Log.w(TAG, "Are Admin Passwords Equal? " + adminPasswordEqual);

        if(sessionPasswordEqual && adminPasswordEqual){
            //add Session Name to database
            ParseObject newSession = new ParseObject("Admin");
            newSession.put("session_name", newSessionName);
            newSession.saveInBackground();

            //add Session PW to database
            newSession.put("session_pass", newSessionPassword);
            newSession.saveInBackground();

            //add Admin PW to database
            newSession.put("admin_pass", newAdminPassword);
            newSession.saveInBackground();

            //go to next page
            Intent intent = new Intent(this, NewCategoryActivity.class);
            startActivity(intent);

        } else if (!sessionPasswordEqual && adminPasswordEqual){
            Toast.makeText(this, "Session Password not equal. Please type in again.", Toast.LENGTH_SHORT).show();
        } else if (sessionPasswordEqual && !adminPasswordEqual){
            Toast.makeText(this, "Admin Password not equal. Please type in again.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Both the Session Password and Admin Password are not equal. Please type in again.", Toast.LENGTH_SHORT).show();
        }

    }
}
