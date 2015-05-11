package com.example.emilyleazer11.votingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;


public class JoinSessionActivity extends Activity {

    public static final String SESSION_EXTRA = "Session";

    public static String activePosition;

    //public static final String TAG = "Voting App Activity";

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
        try {
            TextView sessionName = (TextView) findViewById(R.id.sessionNameEditText);
            String sessionNameValue = sessionName.getText().toString();
            TextView sessionPass = (TextView) findViewById(R.id.sessionPasswordEditText);
            String passValue = sessionPass.getText().toString();
            attemptJoinSession(sessionNameValue, passValue, sessionName, sessionPass);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

//    public void successPull(String string) {
//        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
//    }


    public void attemptJoinSession(String session,String pass, TextView sessionName, TextView sessionPass) throws ParseException {
        final String attemptedPass = pass;
        final TextView sessionNameText = sessionName;
        final TextView sessionPassText = sessionPass;
        ParseQuery<Session> query = ParseQuery.getQuery("Sessions");
        query.whereEqualTo("session_name", session);
        query.findInBackground(new FindCallback<Session>() {
            public void done(List<Session> sessionList, ParseException e) {
                if (e == null) {
                    if ((sessionList.size() != 0) && (sessionList.get(0).isPassCorrect(attemptedPass)))
                        joinSuccess(sessionNameText.getText().toString());
                    else
                        joinFail(sessionNameText, sessionPassText);
                } else {
                    Log.w("session_name", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void joinFail(TextView sessionName, TextView sessionPass) {
        sessionName.setText("");
        sessionPass.setText("");
        Toast.makeText(this, "The session or password is incorrect, or does not exist.",
                Toast.LENGTH_SHORT).show();
    }

    public void joinSuccess(String sessionName) {
        Intent intent = new Intent (this, SessionActivity.class);

        //REED I NEED HELP WITH THIS ---> grab the name of the active category that admin user began and save as categoryName variable
        String categoryName = getActivePosition();
        //done.
        intent.putExtra(SESSION_EXTRA, sessionName);
        startActivity(intent);
    }

    public String getActivePosition() {
        ParseQuery<Candidate> actives = ParseQuery.getQuery("Candidate");
        actives.whereEqualTo("active",true);
        //will need to make this specific to the session we're in
        actives.findInBackground(new FindCallback<Candidate>() {
            public void done(List<Candidate> activeCandidates, ParseException e) {
                if (e == null) {
                    setActivePosition(activeCandidates.get(0).getPosition());
                } else {
                    Log.w("session_name", "Error: " + e.getMessage());
                }
            }
        });
        return activePosition;
    }

    public void setActivePosition(String newPosition) {
        activePosition = newPosition;
    }
}

