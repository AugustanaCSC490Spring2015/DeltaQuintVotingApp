package com.example.emilyleazer11.votingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class AdminLoginActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
    }

    public void adminLogin(View view){
        //
        // write code to check password
        //
        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
    }

}
