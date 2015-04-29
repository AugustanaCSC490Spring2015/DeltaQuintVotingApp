package com.example.emilyleazer11.votingapp;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Reed on 4/29/2015.
 */

@ParseClassName("Sessions")
public class Session extends ParseObject {
    public String getName() {
        return getString("session_name");
    }

    public String getPass(){
        return getString("session_pass");
    }

    public String getAdminPass() {
        return getString("admin_pass");
    }

    public void setName(String name) {
        put("session_name",name);
    }

    public void setPass(String pass) {
        put("session_pass",pass);
    }

    public void setAdminPass(String pass) {
        put("admin_pass",pass);
    }

    public boolean isPassCorrect(String attemptPass) {
        if (attemptPass.equals(getPass())) {
            return true;
        } else return false;
    }

    public boolean isAdminPassCorrect(String attemptPass) {
        if (attemptPass.equals(getAdminPass())) {
            return true;
        } else return false;
    }
}

