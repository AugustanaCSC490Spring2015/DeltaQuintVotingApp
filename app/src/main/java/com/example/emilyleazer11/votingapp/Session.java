package com.example.emilyleazer11.votingapp;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Reed on 4/29/2015.
 *
 * Each session object represents an object from the database "Session"
 *      and each session has a name, pass and admin pass
 */

@ParseClassName("Sessions")
public class Session extends ParseObject {

    //this method returns the name of the given session
    public String getName() {
        return getString("session_name");
    }

    //this method returns the password of the session
    public String getPass(){
        return getString("session_pass");
    }

    //this method returns the admin password of the session
    public String getAdminPass() {
        return getString("admin_pass");
    }

    //This method sets the name of the session
    public void setName(String name) {
        put("session_name",name);
    }

    //this method sets the password of the session
    public void setPass(String pass) {
        put("session_pass",pass);
    }

    //this method sets the admin pass of the session
    public void setAdminPass(String pass) {
        put("admin_pass",pass);
    }

    //this method returns the boolean value of whether the entered password
    //matches the password of the session
    public boolean isPassCorrect(String attemptPass) {
        if (attemptPass.equals(getPass())) {
            return true;
        } else return false;
    }

    //this method returns the boolean value of whether the entered admin pass
    //matches the admin password of the session
    public boolean isAdminPassCorrect(String attemptPass) {
        if (attemptPass.equals(getAdminPass())) {
            return true;
        } else return false;
    }
}

