package com.example.emilyleazer11.votingapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SessionActivity extends Activity implements OnItemClickListener {

    private ArrayList<String> candidates;
    private ArrayList<RadioButton> radioButtons;

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        Intent starterIntent = this.getIntent();
        String sessionIntent = starterIntent.getStringExtra(JoinSessionActivity.SESSION_EXTRA);

        TextView session = (TextView) this.findViewById(R.id.sessionName);
        session.setText(sessionIntent);

        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(submitClickListener);

        populateCandidates();
    }


    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        String chosenOne = ((TextView) view).getText().toString();
        Toast.makeText(this, chosenOne, Toast.LENGTH_LONG).show();
    }

    View.OnClickListener submitClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            submitVote();
        }
    };

    public void submitVote(){
        checkVote();

        Intent intent = new Intent(this, EndSessionActivity.class);
        startActivity(intent);
    }

    public void checkVote() {
        Intent starterIntent = this.getIntent();
        final String sessionIntent = starterIntent.getStringExtra(JoinSessionActivity.SESSION_EXTRA);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        RadioButton chosenCandidate = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        String chosenCandidateString = (String) chosenCandidate.getText();

        ParseQuery<Candidate> actives = ParseQuery.getQuery("Candidate");
        actives.whereEqualTo("active",true);
        actives.whereEqualTo("session_name",sessionIntent);
        actives.whereEqualTo("candidate_name",chosenCandidateString);
        actives.findInBackground(new FindCallback<Candidate>() {
            public void done(List<Candidate> activeCandidates, ParseException e) {
                if (e == null) {
                    activeCandidates.get(0).addOneVote(sessionIntent);
                    activeCandidates.get(0).saveInBackground();
                } else {
                    Log.w("session_name", "Error: " + e.getMessage());
                }
            }
        });

        //Toast.makeText(this,"Your vote has been recorded!", Toast.LENGTH_SHORT).show();

    }

    //query: pull all candidates of one session and one category
    public void populateCandidates() {
        Intent starterIntent = this.getIntent();
        final String sessionIntent = starterIntent.getStringExtra(JoinSessionActivity.SESSION_EXTRA);

        final TextView categoryText = (TextView) findViewById(R.id.categoryName);

        ParseQuery<Candidate> actives = ParseQuery.getQuery("Candidate");
        actives.whereEqualTo("active",true);
        actives.whereEqualTo("session_name",sessionIntent);
        actives.findInBackground(new FindCallback<Candidate>() {
            public void done(List<Candidate> activeCandidates, ParseException e) {
                if (e == null) {
                    fillList(activeCandidates);
                    categoryText.setText(activeCandidates.get(0).getPosition());
                } else {
                    Log.w("session_name", "Error: " + e.getMessage());
                }
            }
        });

    }

    public void fillList(List<Candidate> candidateList) {
//        Toast.makeText(this, candidateList.size(), Toast.LENGTH_SHORT).show();
        RadioButton radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton1.setVisibility(View.INVISIBLE);
        RadioButton radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton2.setVisibility(View.INVISIBLE);
        RadioButton radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton3.setVisibility(View.INVISIBLE);
        RadioButton radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
        radioButton4.setVisibility(View.INVISIBLE);
        RadioButton radioButton5 = (RadioButton) findViewById(R.id.radioButton5);
        radioButton5.setVisibility(View.INVISIBLE);
        RadioButton radioButton6 = (RadioButton) findViewById(R.id.radioButton6);
        radioButton6.setVisibility(View.INVISIBLE);
        RadioButton radioButton7 = (RadioButton) findViewById(R.id.radioButton7);
        radioButton7.setVisibility(View.INVISIBLE);
        RadioButton radioButton8 = (RadioButton) findViewById(R.id.radioButton8);
        radioButton8.setVisibility(View.INVISIBLE);
        radioButton1.setText(candidateList.get(0).getCandidateName());

        int numCandidates = candidateList.size();

        if (numCandidates == 0) {
            Toast.makeText(this, "No Canidates", Toast.LENGTH_SHORT);
        } else {
            if (numCandidates >= 1) {
                radioButton1.setText(candidateList.get(0).getCandidateName());
                radioButton1.setVisibility(View.VISIBLE);
            }
            if (numCandidates >= 2) {
                radioButton2.setText(candidateList.get(1).getCandidateName());
                radioButton2.setVisibility(View.VISIBLE);
            }
            if (numCandidates >= 3) {
                radioButton3.setText(candidateList.get(2).getCandidateName());
                radioButton3.setVisibility(View.VISIBLE);
            }
            if (numCandidates >= 4) {
                radioButton4.setText(candidateList.get(3).getCandidateName());
                radioButton4.setVisibility(View.VISIBLE);
            }
            if (numCandidates >= 5) {
                radioButton5.setText(candidateList.get(4).getCandidateName());
                radioButton5.setVisibility(View.VISIBLE);
            }
            if (numCandidates >= 6) {
                radioButton6.setText(candidateList.get(5).getCandidateName());
                radioButton6.setVisibility(View.VISIBLE);
            }
            if (numCandidates >= 7) {
                radioButton7.setText(candidateList.get(6).getCandidateName());
                radioButton7.setVisibility(View.VISIBLE);
            }
            if (numCandidates == 8) {
                radioButton8.setText(candidateList.get(7).getCandidateName());
                radioButton8.setVisibility(View.VISIBLE);
            }
        }
    }

    public void populateRadioButtons() {
        RadioButton radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton1.setVisibility(View.INVISIBLE);
        RadioButton radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton2.setVisibility(View.INVISIBLE);
        RadioButton radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton3.setVisibility(View.INVISIBLE);
        RadioButton radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
        radioButton4.setVisibility(View.INVISIBLE);
        RadioButton radioButton5 = (RadioButton) findViewById(R.id.radioButton5);
        radioButton5.setVisibility(View.INVISIBLE);
        RadioButton radioButton6 = (RadioButton) findViewById(R.id.radioButton6);
        radioButton6.setVisibility(View.INVISIBLE);
        RadioButton radioButton7 = (RadioButton) findViewById(R.id.radioButton7);
        radioButton7.setVisibility(View.INVISIBLE);
        RadioButton radioButton8 = (RadioButton) findViewById(R.id.radioButton8);
        radioButton8.setVisibility(View.INVISIBLE);


    }


}
