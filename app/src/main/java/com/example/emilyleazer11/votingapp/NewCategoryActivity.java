package com.example.emilyleazer11.votingapp;


import android.content.Intent;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.Collections;


public class NewCategoryActivity extends ListActivity {

    private static final String CANDIDATES = "candidates";
    private EditText candidateEditText;
    private SharedPreferences savedCandidates;
    private ArrayList<String> candidates;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

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

    public void launchMainActivity() {
        savedCandidates.edit().clear().commit(); //must clear the list each time you exit the screen
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void launchResultsActivity() {
        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
    }


    public View.OnClickListener addCandidateButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (candidateEditText.getText().length() > 0) {
                addCandidate(candidateEditText.getText().toString());
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
                    }
                }
        );

        confirmBuilder.create().show(); // display AlertDialog
    }
}
