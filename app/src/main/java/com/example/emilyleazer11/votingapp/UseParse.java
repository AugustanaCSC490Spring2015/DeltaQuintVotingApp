package com.example.emilyleazer11.votingapp;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Reed on 4/22/2015.
 */
public class UseParse extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "lI5qHgfDMgt1PTkVJwd9PocgCx8R8ihrUxZP1bMn", "UsJq6UFADAgXgRMhS9O8JpyeeexrdlUg3A5xNqx2");
    }

}