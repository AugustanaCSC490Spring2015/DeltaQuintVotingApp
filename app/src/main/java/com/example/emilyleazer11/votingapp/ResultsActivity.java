package com.example.emilyleazer11.votingapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends ListActivity {

    private TextView resultList;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent starterIntent = this.getIntent();
        String sessionIntent = starterIntent.getStringExtra(NewCategoryActivity.SESSION_EXTRA);

        TextView sessionName = (TextView) this.findViewById(R.id.sessionNameTitle);
        sessionName.setText(sessionIntent);

        Button refreshPage = (Button) findViewById(R.id.refreshPageButton);
        refreshPage.setOnClickListener(refreshPageOnClickListener);

        resultList = (TextView) this.findViewById(R.id.textView3);

        final TextView categoryName = (TextView) this.findViewById(R.id.categoryTitle);
        ParseQuery<Candidate> actives = ParseQuery.getQuery("Candidate");
        actives.whereEqualTo("active",true);
        actives.whereEqualTo("session_name",sessionIntent);
        actives.findInBackground(new FindCallback<Candidate>() {
            public void done(List<Candidate> activeCandidates, ParseException e) {
                if (e == null) {
                    categoryName.setText(activeCandidates.get(0).getPosition());
                    String resultString = "";
                    for (Candidate candidate:activeCandidates){
                        resultString = resultString + candidate.getCandidateName() + "  (" + candidate.getVotes() + ")" + "\n";
                    }
                    resultList.setText(resultString);
                } else {
                    Log.w("session_name", "Error: " + e.getMessage());
                }
            }
        });
    }

    View.OnClickListener refreshPageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            refreshPage(v);
        }
    };

    public void returnHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void refreshPage(View view){
        resultList = (TextView) this.findViewById(R.id.textView3);
        Intent starterIntent = this.getIntent();
        String sessionIntent = starterIntent.getStringExtra(NewCategoryActivity.SESSION_EXTRA);
        ParseQuery<Candidate> actives = ParseQuery.getQuery("Candidate");
        actives.whereEqualTo("active",true);
        actives.whereEqualTo("session_name",sessionIntent);
        actives.findInBackground(new FindCallback<Candidate>() {
            public void done(List<Candidate> activeCandidates, ParseException e) {
                if (e == null) {
                    String resultString = "";
                    for (Candidate candidate:activeCandidates){
                        resultString = resultString + candidate.getCandidateName() + "  (" + candidate.getVotes() + ")" + "\n";
                    }
                    resultList.setText(resultString);
                } else {
                    Log.w("session_name", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void deleteSession(View view){
        Intent starterIntent = this.getIntent();
        String sessionIntent = starterIntent.getStringExtra(NewCategoryActivity.SESSION_EXTRA);
        ParseQuery<Candidate> removeCandidate = ParseQuery.getQuery("Candidate");
        removeCandidate.whereEqualTo("session_name",sessionIntent);
        removeCandidate.findInBackground(new FindCallback<Candidate>() {
            public void done(List<Candidate> candidatesToDelete, ParseException e) {
                if (e == null) {
                    for (Candidate candidate : candidatesToDelete) {
                        candidate.deleteInBackground();
                    }
                } else {
                    Log.w("session_name", "Error: " + e.getMessage());
                }
            }
        });
        ParseQuery<Session> removeSession = ParseQuery.getQuery("Sessions");
        removeSession.whereEqualTo("session_name",sessionIntent);
        removeSession.findInBackground(new FindCallback<Session>() {
            public void done(List<Session> sessionToDelete, ParseException e) {
                if (e == null) {
                    for (Session session: sessionToDelete) {
                        session.deleteInBackground();
                    }
                } else {
                    Log.w("session_name", "Error: " + e.getMessage());
                }
            }
        });
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void addNewCategory(View view) {
        //this should set all active positions to false
        Intent starterIntent = this.getIntent();
        String sessionIntent = starterIntent.getStringExtra(NewCategoryActivity.SESSION_EXTRA);
        ParseQuery<Candidate> actives = ParseQuery.getQuery("Candidate");
        actives.whereEqualTo("active",true);
        actives.whereEqualTo("session_name",sessionIntent);
        actives.findInBackground(new FindCallback<Candidate>() {
            public void done(List<Candidate> activeCandidates, ParseException e) {
                if (e == null) {
                    for (Candidate candidate:activeCandidates) {
                       candidate.setActive(Boolean.FALSE);
                       candidate.saveInBackground();
                    }
                } else {
                    Log.w("session_name", "Error: " + e.getMessage());
                }
            }
        });
        Intent intent = new Intent(this, NewCategoryActivity.class);
        startActivity(intent);
    }

}
