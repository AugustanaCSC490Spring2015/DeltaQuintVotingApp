package com.example.emilyleazer11.votingapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // opens the activity to create a new session
    public void createNewSession(View view){
        Intent intent = new Intent(this, CreateSessionActivity.class);
        startActivity(intent);
    }

    // begins the process for users to vote
    public void joinExistingSession(View view){
        Intent intent = new Intent (this, JoinSessionActivity.class);
        startActivity(intent);
    }

    // begins the login for the admins to view the results and do admin duties
    public void resultsLogin(View view){
        Intent intent = new Intent (this, AdminLoginActivity.class);
        startActivity(intent);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
