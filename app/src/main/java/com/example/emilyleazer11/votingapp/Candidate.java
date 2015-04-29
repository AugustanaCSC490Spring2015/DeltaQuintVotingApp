package com.example.emilyleazer11.votingapp;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Reed on 4/29/2015.
 */
@ParseClassName("Sessions")
public class Candidate extends ParseObject {
    public String getSession() {
        return getString("session_name");
    }

    public String getPosition() {
        return getString("position");
    }

    public String getCandidateName() {
        return getString("candidate_name");
    }

    public int vote_count() {
        return getInt("vote_count");
    }

}