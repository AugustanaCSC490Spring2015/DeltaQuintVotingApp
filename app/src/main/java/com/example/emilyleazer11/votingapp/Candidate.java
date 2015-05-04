package com.example.emilyleazer11.votingapp;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Reed on 4/29/2015.
 */
@ParseClassName("Candidate")
public class Candidate extends ParseObject {

    public String getPosition() {
        return getString("position");
    }

    public String getSession() {
        return getString("session_name");
    }

    public Integer getVotes() {
        return getInt("vote_count");
    }

    public String getCandidateName() {
        return getString("candidate_name");
    }

    public void setName(String name) {
        put("candidate_name",name);
    }

    public void setPosition(String position) {
        put("position",position);
    }

    public void setSession(String session) {
        put("session_name",session);
    }

    public void addOneVote(String session) {
        int count = getVotes();
        count++;
        put("vote_count",count);
    }

}