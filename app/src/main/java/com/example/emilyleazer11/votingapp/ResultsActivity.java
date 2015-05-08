package com.example.emilyleazer11.votingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class ResultsActivity extends Activity{

    Intent starterIntent = this.getIntent();
    public final String sessionIntent = starterIntent.getStringExtra(NewCategoryActivity.SESSION_EXTRA);
    public final String categoryIntent = starterIntent.getStringExtra(NewCategoryActivity.CATEGORY_EXTRA);

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        TextView sessionName = (TextView) this.findViewById(R.id.sessionNameEditText);
        sessionName.setText(sessionIntent);

        TextView categoryName = (TextView) this.findViewById(R.id.categoryNameEditText);
        categoryName.setText(categoryIntent);
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

}
