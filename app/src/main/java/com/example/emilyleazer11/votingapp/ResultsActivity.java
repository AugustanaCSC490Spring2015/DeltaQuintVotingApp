package com.example.emilyleazer11.votingapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends ListActivity {

    public final String TAG = "Voting App";
    public ArrayList<String> listOfCandidates;
    private String activePosition;
    private ArrayAdapter<String> adapter;

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent starterIntent = this.getIntent();
        String sessionIntent = starterIntent.getStringExtra(NewCategoryActivity.SESSION_EXTRA);

        TextView sessionName = (TextView) this.findViewById(R.id.sessionNameTitle);
        sessionName.setText(sessionIntent);

        final TextView categoryName = (TextView) this.findViewById(R.id.categoryTitle);
        ParseQuery<Candidate> actives = ParseQuery.getQuery("Candidate");
        actives.whereEqualTo("active",true);
        actives.whereEqualTo("session_name",sessionIntent);
        actives.findInBackground(new FindCallback<Candidate>() {
            public void done(List<Candidate> activeCandidates, ParseException e) {
                if (e == null) {
                    categoryName.setText(activeCandidates.get(0).getPosition());
                } else {
                    Log.w("session_name", "Error: " + e.getMessage());
                }
            }
        });

        ParseQuery<Candidate> activeCandidates = ParseQuery.getQuery("Candidate");
        activeCandidates.whereEqualTo("active",true);
        activeCandidates.whereEqualTo("session_name",sessionIntent);
        activeCandidates.findInBackground(new FindCallback<Candidate>() {
            public void done(List<Candidate> activeCandidates, ParseException e) {
                if (e == null) {
                    for (Candidate candidate:activeCandidates){
                        listOfCandidates.add(candidate.getCandidateName());
                    }
                } else {
                    Log.w("session_name", "Error: " + e.getMessage());
                }
            }
        });

        adapter = new ArrayAdapter<String>(this, R.layout.list_item, listOfCandidates);
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();


    }


    public void returnHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void refreshPage(View view){
        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
    }

    public void deleteSession(View view){
        //
        // Write code to delete sessions info from the database
        //
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addNewCategory(View view) {
        //
        // Add code to deactivate current session and add in the new session
        //
        Intent intent = new Intent(this, NewCategoryActivity.class);
        startActivity(intent);
    }

    public void populateList(){
        Intent starterIntent = this.getIntent();
        String sessionIntent = starterIntent.getStringExtra(NewCategoryActivity.SESSION_EXTRA);

        ParseQuery<Candidate> actives = ParseQuery.getQuery("Candidate");
        actives.whereEqualTo("active",true);
        actives.whereEqualTo("session_name",sessionIntent);
        actives.findInBackground(new FindCallback<Candidate>() {
            public void done(List<Candidate> activeCandidates, ParseException e) {
                if (e == null) {
                    populateListWithDatabaseEntries(activeCandidates);
                } else {
                    Log.w("session_name", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void populateListWithDatabaseEntries(List<Candidate> activeCandidates){
        for (Candidate candidate:activeCandidates){

        }

    }



}
