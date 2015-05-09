package com.example.emilyleazer11.votingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class ResultsActivity extends Activity{

    Intent starterIntent = this.getIntent();
    public final String sessionIntent = starterIntent.getStringExtra(NewCategoryActivity.SESSION_EXTRA);
    public final String categoryIntent = starterIntent.getStringExtra(NewCategoryActivity.CATEGORY_EXTRA);
    public String activePosition;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        TextView sessionName = (TextView) this.findViewById(R.id.sessionNameEditText);
        sessionName.setText(sessionIntent);

        TextView categoryName = (TextView) this.findViewById(R.id.categoryNameEditText);
        categoryName.setText(getActivePosition());
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

    public String getActivePosition() {
        ParseQuery<Candidate> actives = ParseQuery.getQuery("Candidate");
        actives.whereEqualTo("active",true);
        actives.whereEqualTo("session_name",sessionIntent);
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
