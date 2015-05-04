package com.example.emilyleazer11.votingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by MHanson on 4/20/2015.
 */
public class SessionActivity extends Activity {
    //there will need to be some variable, maybe passed by the intent, or by the sharedPrefManager
    // that will define what sesion this user is logged into and what category is up
    //right now i have itset to "NewSession"
    public final String sessionName = "NewSession";
    public final String currentCategory = "Treasurer";
    Intent starterIntent = this.getIntent();
    public final String sessionIntent = starterIntent.getStringExtra(JoinSessionActivity.SESSION_EXTRA);
    //public final String categoryIntent = starterIntent.getStringExtra(JoinSessionActivity.CATEGORY_EXTRA):

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        //Intent starterIntent = this.getIntent();
        //String sessionName = starterIntent.getStringExtra(JoinSessionActivity.SESSION_EXTRA);


        TextView category = (TextView) this.findViewById(R.id.categoryName);
        //category.setText(categoryIntent);
        TextView session = (TextView) this.findViewById(R.id.sessionName);
        session.setText(sessionIntent);

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

    //query: pul all candidates of one session and one category
    public void populateCandidates() {
        ParseQuery<Candidate> query = ParseQuery.getQuery("Candidates");
        query.whereEqualTo("session_name", sessionName);
        query.whereEqualTo("position", currentCategory);
        query.findInBackground(new FindCallback<Candidate>() {
            public void done(List<Candidate> candidateList, ParseException e) {
                if (e == null) {
                    fillList(candidateList, currentCategory);
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
      final String categoryName = category;
      for (Candidate candidate: candidateList) {
          candidate.getCandidateName();
          //this will loop through all of the candidate objects and return all of the names
      }
    }





}
