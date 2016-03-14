package com.example.nicolassaad.neighborhoodguideapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        /**
         * Begin method invocations for DetailActivity
         */
        setTitle();

        /**
         * end method invocations
         */
    }

    /**
     * setTitle takes the result that the user clicked on from the ListView in ResultsActivity and
     * sets it to be the title of this activity.
     */
    private void setTitle() {
        Intent receivingResultsIntent = getIntent();
        String barTitle = receivingResultsIntent.getStringExtra(ResultsActivity.DATA_LIST_TILE);
        setTitle(barTitle);

    }
}
