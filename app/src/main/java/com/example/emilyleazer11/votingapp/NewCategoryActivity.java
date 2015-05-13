package com.example.emilyleazer11.votingapp;


import android.content.Intent;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

import org.w3c.dom.Text;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class NewCategoryActivity extends ListActivity {


    private String categoryName;
    //private String candidateName;
    public static final String SESSION_EXTRA = "Session";
    public static final String TAG = "Voting App";


    private static final String CANDIDATES = "candidates";
    private EditText candidateEditText;
    private SharedPreferences savedCandidates;
    private ArrayList<String> candidates;
    private ArrayAdapter<String> adapter;
    private boolean ready = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        Intent starterIntent = this.getIntent();
        String sessionIntent = starterIntent.getStringExtra(CreateSessionActivity.SESSION_EXTRA);

        Log.w(TAG, "Got session = " + sessionIntent);

        Button endVotingSessionButton = (Button) findViewById(R.id.endVotingSessionButton);
        endVotingSessionButton.setOnClickListener(endSessionClickListener);

        candidateEditText = (EditText) findViewById(R.id.candidateEditText);
        savedCandidates = getSharedPreferences(CANDIDATES, MODE_PRIVATE);

        // store the saved candidates in an ArrayList then sort them
        candidates = new ArrayList<String>(savedCandidates.getAll().keySet());
        Collections.sort(candidates, String.CASE_INSENSITIVE_ORDER);

        //binds the candidates to the list
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, candidates);
        setListAdapter(adapter);

        ImageButton addCandidateButton = (ImageButton) findViewById(R.id.addCandidateButton);
        addCandidateButton.setOnClickListener(addCandidateButtonListener);

        getListView().setOnItemLongClickListener(candidateLongClickListener);

        Button viewResultsButton = (Button) findViewById(R.id.viewResultsButton);
        viewResultsButton.setOnClickListener(viewResultsClickListener);

        Button deleteSession = (Button) findViewById(R.id.deleteThisSessionButton);
        deleteSession.setOnClickListener(deleteSessionClickListener);

    }

    View.OnClickListener endSessionClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            launchMainActivity();
        }
    };

    View.OnClickListener viewResultsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            launchResultsActivity();
        }
    };

    View.OnClickListener deleteSessionClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            deleteSession(v);
        }
    };

    public void launchMainActivity() {
        savedCandidates.edit().clear().commit(); //must clear the list each time you exit the screen
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addCategoryName(View view) {
        // code to add the category name to database
        // categoryName = ;
        // Disables the Category Name Fields
        EditText tempA  = (EditText) findViewById(R.id.categoryNameEditText);
        tempA.setEnabled(false);
        ImageButton tempB  = (ImageButton) findViewById(R.id.addCategoryNameButton);
        tempB.setEnabled(false);
        // Enables the Candidate Name Fields
        tempA  = (EditText) findViewById(R.id.candidateEditText);
        tempA.setEnabled(true);
        tempB  = (ImageButton) findViewById(R.id.addCandidateButton);
        tempB.setEnabled(true);
        Button tempC  = (Button) findViewById(R.id.viewResultsButton);
        tempC.setEnabled(true);
    }



    public void launchResultsActivity() {
        attemptActivateCategory();
    }


    public View.OnClickListener addCandidateButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (candidateEditText.getText().length() > 0) {
                EditText categoryText = (EditText) findViewById(R.id.categoryNameEditText);
                categoryName = categoryText.getText().toString();

                EditText candidateText = (EditText) findViewById(R.id.candidateEditText);
                String candidateName = candidateText.getText().toString();

                addCandidate(candidateName);
                candidateEditText.setText("");
            } else {
                // create a new AlertDialog Builder
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(NewCategoryActivity.this);

                // set dialog's message to display
                builder.setMessage("No candidate entered.");

                // provide an OK button that simply dismisses the dialog
                builder.setPositiveButton("Ok", null);

                // create AlertDialog from the AlertDialog.Builder
                AlertDialog errorDialog = builder.create();
                errorDialog.show(); // display the modal dialog
            }
        }
    };

    private void addCandidate(String candidateName) {
        SharedPreferences.Editor preferencesEditor = savedCandidates.edit();
        preferencesEditor.putString(candidateName, candidateName);
        preferencesEditor.apply();

        if (!candidates.contains(candidateName)) {
            candidates.add(candidateName);
            Collections.sort(candidates, String.CASE_INSENSITIVE_ORDER);
            adapter.notifyDataSetChanged();
            addCandidateToDatabase(candidateName);
        }
    }

    OnItemLongClickListener candidateLongClickListener = new OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            final String savedCandidate = ((TextView) view).getText().toString();

            AlertDialog.Builder builder = new AlertDialog.Builder(NewCategoryActivity.this);

            builder.setTitle(getString(R.string.shareEditDeleteTitle, savedCandidate));

            builder.setItems(R.array.dialog_items,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0: //edit
                                    candidateEditText.setText(savedCandidates.getString(savedCandidate, ""));
                                    editCandidate(savedCandidate);
                                    break;
                                case 1: //delete
                                    deleteCandidate(savedCandidate);
                                    break;


                            }
                        }

                    }
            );

            builder.setNegativeButton(getString(R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    }
            );

            builder.create().show();
            return true;

        }
    };

    private void editCandidate(final String candidate) {
        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(this);
        confirmBuilder.setMessage("Are you sure you want to edit this candidate?");

        confirmBuilder.setNegativeButton(getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }

                }
        );

        confirmBuilder.setPositiveButton(getString(R.string.OK),
                new DialogInterface.OnClickListener() {
                    // called when "Cancel" Button is clicked
                    public void onClick(DialogInterface dialog, int id) {
                        candidates.remove(candidate); // remove tag from quantities
                        removeCandidateFromDatabase(candidate);

                        // get SharedPreferences.Editor to remove saved candidate
                        SharedPreferences.Editor preferencesEditor = savedCandidates.edit();
                        preferencesEditor.remove(candidate); // remove candidate
                        preferencesEditor.apply(); // saves the changes

                        // rebind candidate ArrayList to ListView to show updated list
                        adapter.notifyDataSetChanged();
                    }
                } // end OnClickListener
        ); // end call to setPositiveButton

        confirmBuilder.create().show();
    }

    private void deleteCandidate(final String candidate) {
        // create a new AlertDialog
        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(this);

        // set the AlertDialog's message
        confirmBuilder.setMessage(
                getString(R.string.confirmMessage, candidate));

        // set the AlertDialog's negative Button
        confirmBuilder.setNegativeButton(getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    // called when "Cancel" Button is clicked
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel(); // dismiss dialog
                    }
                }
        );

        // set the AlertDialog's positive Button
        confirmBuilder.setPositiveButton(getString(R.string.delete),
                new DialogInterface.OnClickListener() {
                    // called when "Cancel" Button is clicked
                    public void onClick(DialogInterface dialog, int id) {
                        candidates.remove(candidate); // remove candidate from ArrayList candidates

                        // get SharedPreferences.Editor to remove saved candidate
                        SharedPreferences.Editor preferencesEditor = savedCandidates.edit();
                        preferencesEditor.remove(candidate); // remove candidate
                        preferencesEditor.apply(); // saves the changes

                        // rebind candidate ArrayList to ListView to show updated list
                        adapter.notifyDataSetChanged();

                        removeCandidateFromDatabase(candidate);
                    }
                }
        );

        confirmBuilder.create().show(); // display AlertDialog
    }

    public void addCandidateToDatabase(String candidate) {
        Intent starterIntent = this.getIntent();
        String sessionIntent = starterIntent.getStringExtra(CreateSessionActivity.SESSION_EXTRA);
        Candidate newCandidate = new Candidate();
        newCandidate.setName(candidate);
        newCandidate.setPosition(categoryName);
        newCandidate.setSession(sessionIntent);
        newCandidate.setVoteCount(0);
        newCandidate.setActive(Boolean.FALSE);
        newCandidate.saveInBackground();
    }

    public void attemptActivateCategory() {
        Intent starterIntent = this.getIntent();
        String sessionIntent = starterIntent.getStringExtra(CreateSessionActivity.SESSION_EXTRA);
        activateCurrentPosition(categoryName);
        checkReady();
        if (ready) {
            Intent intent = new Intent(this, ResultsActivity.class);
            intent.putExtra(SESSION_EXTRA, sessionIntent);
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(this, "Please Wait", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void checkReady() {
        Intent starterIntent = this.getIntent();
        String sessionIntent = starterIntent.getStringExtra(CreateSessionActivity.SESSION_EXTRA);
        ParseQuery<Candidate> actives = ParseQuery.getQuery("Candidate");
        actives.whereEqualTo("active",true);
        actives.whereEqualTo("session_name",sessionIntent);
        actives.findInBackground(new FindCallback<Candidate>() {
            public void done(List<Candidate> activeCandidates, ParseException e) {
                if (e == null) {
                    if (activeCandidates.size() > 0) {
                        setReady();
                    }
                } else {
                    Log.w("session_name", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void activateCurrentPosition(String position) {
        Intent starterIntent = this.getIntent();
        String sessionIntent = starterIntent.getStringExtra(CreateSessionActivity.SESSION_EXTRA);
        ParseQuery<Candidate> activatePosition = ParseQuery.getQuery("Candidate");
        activatePosition.whereEqualTo("position",position);
        activatePosition.whereEqualTo("session_name",sessionIntent);
        activatePosition.findInBackground(new FindCallback<Candidate>() {
            public void done(List<Candidate> candidatesToActivate, ParseException e) {
                if (e == null) {
                    for (Candidate candidate : candidatesToActivate) {
                        candidate.setActive(Boolean.TRUE);
                        candidate.saveInBackground();
                    }
                } else {
                    Log.w("session_name", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void removeCandidateFromDatabase(String candidate) {
        Intent starterIntent = this.getIntent();
        String sessionIntent = starterIntent.getStringExtra(CreateSessionActivity.SESSION_EXTRA);
        ParseQuery<Candidate> removeCandidate = ParseQuery.getQuery("Candidate");
        removeCandidate.whereEqualTo("candidate_name", candidate);
        removeCandidate.whereEqualTo("session_name",sessionIntent);
        removeCandidate.findInBackground(new FindCallback<Candidate>() {
            public void done(List<Candidate> candidatesToDelete, ParseException e) {
                if (e == null) {
                   for (Candidate candidate: candidatesToDelete) {
                       candidate.deleteInBackground();
                    }
                } else {
                    Log.w("session_name", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void setReady() {
        ready = true;
    }

    public void deleteSession(View view){
        Intent starterIntent = this.getIntent();
        String sessionIntent = starterIntent.getStringExtra(NewCategoryActivity.SESSION_EXTRA);
        ParseQuery<Candidate> removeCandidate = ParseQuery.getQuery("Candidate");
        removeCandidate.whereEqualTo("session_name",sessionIntent);
        removeCandidate.findInBackground(new FindCallback<Candidate>() {
            public void done(List<Candidate> candidatesToDelete, ParseException e) {
                if (e == null) {
                    for (Candidate candidate : candidatesToDelete) {
                        candidate.deleteInBackground();
                    }
                } else {
                    Log.w("session_name", "Error: " + e.getMessage());
                }
            }
        });
        ParseQuery<Session> removeSession = ParseQuery.getQuery("Sessions");
        removeSession.whereEqualTo("session_name",sessionIntent);
        removeSession.findInBackground(new FindCallback<Session>() {
            public void done(List<Session> sessionToDelete, ParseException e) {
                if (e == null) {
                    for (Session session: sessionToDelete) {
                        session.deleteInBackground();
                    }
                } else {
                    Log.w("session_name", "Error: " + e.getMessage());
                }
            }
        });
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
