package com.example.emilyleazer11.votingapp;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Reed on 4/29/2015.
 * This class represents a single candidate object from the database, with different
 *     fields position, session, number of votes, and name of candidate
 */
@ParseClassName("Candidate")
public class Candidate extends ParseObject {

    // returns the position of the candidate
    public String getPosition() {
        return getString("position");
    }

    //returns the session of the candidate
    public String getSession() {
        return getString("session_name");
    }

    //returns the number of votes for the given candidate
    public Integer getVotes() {
        return getInt("vote_count");
    }

    //returns the name of the given candidate
    public String getCandidateName() {
        return getString("candidate_name");
    }

    //sets the name of the candidate to a new value, the passed in paramater "name"
    public void setName(String name) {
        put("candidate_name",name);
    }

    //sets the position of the given candidate, based on the passed in paramater "position"
    public void setPosition(String position) {
        put("position",position);
    }

    //sets the session of the given candidate, based on the passed in paramater "session"
    public void setSession(String session) {
        put("session_name",session);
    }

    //sets the vote count of the given candidate to the passed in paramater, an integer "count"
    public void setVoteCount(int count) {
        put("vote_count",count);
    }

    //adds one to the vote count of the candidate
    public void addOneVote(String session) {
        int count = getVotes();
        count++;
        put("vote_count",count);
    }

    //sets the active status of the candidate to the boolean paramater "active"
    //  if the candidates active status is true, they can be voted on
    public void setActive(Boolean active) {
        put("active",active);
    }

}