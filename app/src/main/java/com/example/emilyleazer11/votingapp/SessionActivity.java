package com.example.emilyleazer11.votingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SessionActivity extends Activity {
    List<Map<String, String>> candidateMappedList = new ArrayList<Map<String,String>>();
    //there will need to be some variable, maybe passed by the intent, or by the sharedPrefManager
    // that will define what sesion this user is logged into and what category is up
    //right now i have itset to "NewSession"

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        Intent starterIntent = this.getIntent();
        String sessionIntent = starterIntent.getStringExtra(JoinSessionActivity.SESSION_EXTRA);

        TextView session = (TextView) this.findViewById(R.id.sessionName);
        session.setText(sessionIntent);

        //populate list of possible candidates to vote for here (in the category: categoryIntent)
        //populateCandidates();

        // beginTest
        generateList();
        ListView lv = (ListView) findViewById(R.id.candidatesListView);
        // this is the adapter that will help show the list
        SimpleAdapter ourSimpleAdapter = new SimpleAdapter(this, candidateMappedList,
                android.R.layout.simple_list_item_1, new String[]{"category"},
                new int[]{android.R.id.text1});
        lv.setAdapter(ourSimpleAdapter);
        // endTest

        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(submitClickListener);
    }

    View.OnClickListener submitClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            submitVote();
        }
    };

    public void submitVote(){
        Intent intent = new Intent(this, EndSessionActivity.class);
        startActivity(intent);
    }

    //query: pull all candidates of one session and one category
    public void populateCandidates() {
        Intent starterIntent = this.getIntent();
        String sessionIntent = starterIntent.getStringExtra(JoinSessionActivity.SESSION_EXTRA);

        ParseQuery<Candidate> query = ParseQuery.getQuery("Candidates");
        query.whereEqualTo("session_name", sessionIntent);
        //query.whereEqualTo("position", categoryIntent);
        query.findInBackground(new FindCallback<Candidate>() {
            public void done(List<Candidate> candidateList, ParseException e) {
                if (e == null) {
                    //fillList(candidateList, categoryIntent);
                    //no error
                } else {
                    Log.w("session_name", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void fillList(List<Candidate> candidateList, String category) {

        //this method should populate the page with all possible candidates.
        //the List object candidateList full of object types "Candidate"
        //will contain all of the candidates in the candidate table
        //that have a session_name of "NewSession" and
        //position of "Treasurer"
        List<Map<String, String>> candidateMappedList = new ArrayList<Map<String,String>>();
        final String categoryName = category;
        for (Candidate candidate: candidateList) {
            String currentCandidate = candidate.getCandidateName();
            candidateMappedList.add(createCandidate(categoryName, currentCandidate));
            //generateList();
        }
        displayList(candidateMappedList);
    }

    public void displayList(List<Map<String, String>> candidateMappedList) {
        ListView lv = (ListView) findViewById(R.id.candidatesListView);

        // this is the adapter that will help show the list
        SimpleAdapter ourSimpleAdapter = new SimpleAdapter(this, candidateMappedList,
                android.R.layout.simple_list_item_1, new String[]{"category"},
                new int[]{android.R.id.text1});
        lv.setAdapter(ourSimpleAdapter);
    }

    private HashMap<String, String> createCandidate(String key, String name) {
        HashMap<String, String> tempCandidate = new HashMap<String, String>();
        tempCandidate.put(key, name);
        return tempCandidate;
    }

    private void generateList() {
        candidateMappedList.add(createCandidate("test", "Erik"));
        candidateMappedList.add(createCandidate("test", "Chris"));
        candidateMappedList.add(createCandidate("test", "Alex"));
    }




}
