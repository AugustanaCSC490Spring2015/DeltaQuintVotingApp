package com.example.emilyleazer11.votingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;


public class AdminLoginActivity extends Activity {

    private String sessionToEnter;
    private String passwordAttempt;
    public static final String SESSION_EXTRA = "Session";

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        Button attemptLogon = (Button) findViewById(R.id.adminLoginButton);
        attemptLogon.setOnClickListener(attemptLogonListener);


    }

    View.OnClickListener attemptLogonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            adminLogin(v);
        }
    };

    // reads the input data and either succeeds or fails to login to the proper admin results page
    public void adminLogin(View view){

        EditText sessionEditText = (EditText) findViewById(R.id.sessionNameEditText);
        sessionToEnter = sessionEditText.getText().toString();

        EditText passwordAttemptEditText = (EditText) findViewById(R.id.sessionPasswordEditText);
        passwordAttempt = passwordAttemptEditText.getText().toString();

        ParseQuery<Session> actives = ParseQuery.getQuery("Sessions");
        actives.whereEqualTo("session_name", sessionToEnter);
        actives.findInBackground(new FindCallback<Session>() {
            public void done(List<Session> activeCandidates, ParseException e) {
                if (e == null && activeCandidates.size()> 0) {
                    if (activeCandidates.get(0).getAdminPass().equals(passwordAttempt)){
                        successLogon();
                    } else {
                        failLogon();
                    }
                } else if (e== null && activeCandidates.size() == 0)
                     failLogonNoSession();
                  else {
                        Log.w("session_name", "Error: " + e.getMessage());
                    }
                }
            });
        }

    // opens to the new activity if login is successful
    public void successLogon() {
        Intent intent = new Intent(this, ResultsActivityAdmin.class);
        intent.putExtra(SESSION_EXTRA,sessionToEnter);
        startActivity(intent);
    }

    // message to show an incorrect password
    public void failLogon() {
        Toast toast = Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT);
        toast.show();
        clearFields();
    }

    // user tried to join a non-existing session and shows the following method
    public void failLogonNoSession() {
        Toast toast = Toast.makeText(this, "A session of this name does not exist", Toast.LENGTH_SHORT);
        toast.show();
        clearFields();
    }

    // clears the fields for a user to re-input data
    public void clearFields() {
        EditText sessionEditText = (EditText) findViewById(R.id.sessionNameEditText);
        sessionEditText.setText("");

        EditText passwordAttemptEditText = (EditText) findViewById(R.id.sessionPasswordEditText);
        passwordAttemptEditText.setText("");
    }

}
