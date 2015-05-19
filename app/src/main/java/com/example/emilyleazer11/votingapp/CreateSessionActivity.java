package com.example.emilyleazer11.votingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by emilyleazer11 on 4/17/2015.
 */
public class CreateSessionActivity extends Activity {

    public static final String SESSION_EXTRA = "Session";
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

    // upon pressing quit, no data is saved and the user is brought back to the main menu
    public void launchMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // takes all of the data input by the user and uploads it to the database
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

        if (newSessionName.equals("") || newSessionPassword.equals("") || newConfirmSessionPassword.equals("") || newAdminPassword.equals("") || newConfirmAdminPassword.equals("")){
            Toast.makeText(this, "Please fill out every field", Toast.LENGTH_SHORT).show();
        } else if (sessionPasswordEqual && adminPasswordEqual) {
            try {
                attemptCreateSession(newSessionName, newSessionPassword,newAdminPassword, sessionNameText,sessionPasswordText,sessionConfirmPasswordText, adminPasswordText, adminConfirmPasswordText);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (!sessionPasswordEqual && adminPasswordEqual){
            Toast.makeText(this, "Session Password not equal. Please type in again.", Toast.LENGTH_SHORT).show();
        } else if (sessionPasswordEqual && !adminPasswordEqual){
            Toast.makeText(this, "Admin Password not equal. Please type in again.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Both the Session Password and Admin Password are not equal. Please type in again.", Toast.LENGTH_SHORT).show();
        }

    }

    // checks to make sure the session information doesn't already exist
    public void attemptCreateSession(String session, String newSessionPass, String newAdminPass, EditText sessionNameText,EditText sessionPasswordText, EditText sessionConfirmPasswordText, EditText adminPasswordText, EditText adminConfirmPasswordText) throws ParseException {
        final String sessionName = session;
        final String sessionPass = newSessionPass;
        final String adminPass = newAdminPass;
        final EditText sessionNameTextField = sessionNameText;
        final EditText sessionPassTextField = sessionPasswordText;
        final EditText sessionPassConfirmTextField = sessionConfirmPasswordText;
        final EditText sessionAdminTextField = adminPasswordText;
        final EditText sessionAdminConfirmTextField = adminConfirmPasswordText;
        ParseQuery<Session> query = ParseQuery.getQuery("Sessions");
        query.whereEqualTo("session_name", session);
        query.findInBackground(new FindCallback<Session>() {
            public void done(List<Session> sessionList, ParseException e) {
                if (e == null) {
                    if (sessionList.size() == 0)
                        createSessionSuccess(sessionName,sessionPass,adminPass);
                    else
                        createSessionFail(sessionNameTextField, sessionPassTextField, sessionPassConfirmTextField, sessionAdminTextField, sessionAdminConfirmTextField);
                } else {
                    Log.w("session_name", "Error: " + e.getMessage());
                }
            }
        });
    }

    // code to push information up to the database
    public void createSessionSuccess(String session, String newSessionPass, String newAdminPassword){
        //add Session Name to database
        Session newSession = new Session();
        newSession.setName(session);
        newSession.saveInBackground();

        //add Session PW to database
        newSession.setPass(newSessionPass);
        newSession.saveInBackground();

        //add Admin PW to database
        newSession.setAdminPass(newAdminPassword);
        newSession.saveInBackground();

        //go to next page
        Intent intent = new Intent(this, NewCategoryActivity.class);
        intent.putExtra(SESSION_EXTRA, session);
        startActivity(intent);
    }

    // makes you repost the information if the session name already exists
    public void createSessionFail(EditText sessionNameTextField, EditText sessionPassTextField, EditText sessionPassConfirmTextField,EditText sessionAdminTextField, EditText sessionAdminConfirmTextField) {
        sessionNameTextField.setText("");
        sessionPassTextField.setText("");
        sessionPassConfirmTextField.setText("");
        sessionAdminTextField.setText("");
        sessionAdminConfirmTextField.setText("");
        Toast.makeText(this, "The session of this name already exists.  Please choose another session name.", Toast.LENGTH_SHORT).show();
    }

}
