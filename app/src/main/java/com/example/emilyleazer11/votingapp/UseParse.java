package com.example.emilyleazer11.votingapp;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by Reed on 4/22/2015.
 *
 * This class initializes the application to be configured with our parse database
 *      also it registers the two database/app classes Session and Candidate
 */
public class UseParse extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Session.class);
        ParseObject.registerSubclass(Candidate.class);
        Parse.initialize(this, "lI5qHgfDMgt1PTkVJwd9PocgCx8R8ihrUxZP1bMn", "UsJq6UFADAgXgRMhS9O8JpyeeexrdlUg3A5xNqx2");

    }

}
