package com.example.emilyleazer11.votingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


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
        public void onClick(View v) {
            joinSession();
        }
    };

    public void joinSession() {
        //Intent intent = new Intent (this, SessionActivity.class);
        //startActivity(intent);
        try {
            TextView sessionName = (TextView) findViewById(R.id.sessionNameEditText);
            String sessionNameValue = sessionName.getText().toString();
            TextView sessionPass = (TextView) findViewById(R.id.sessionPasswordEditText);
            String passValue = sessionPass.getText().toString();
            attemptJoinSession(sessionNameValue, passValue);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void successPull(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }


    public void attemptJoinSession(String session,String pass) throws ParseException {
        final String attemptedPass = pass;
        ParseQuery<Session> query = ParseQuery.getQuery("Sessions");
        query.whereEqualTo("session_name", session);
        query.findInBackground(new FindCallback<Session>() {
            public void done(List<Session> sessionList, ParseException e) {
                if (e == null) {
                    if ((sessionList.size() != 0) && (sessionList.get(0).isPassCorrect(attemptedPass))) successPull("success");
                    else successPull("fail");
                    //this conditional should be changed so if it is true, they should go to the next page
                    // and if it is false, clear the field and prompt user that user/pass failed
                   
                } else {
                    Log.w("session_name", "Error: " + e.getMessage());
                }
            }
        });
    }
}

