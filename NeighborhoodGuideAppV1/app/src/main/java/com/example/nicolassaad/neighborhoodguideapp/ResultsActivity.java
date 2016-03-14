package com.example.nicolassaad.neighborhoodguideapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class ResultsActivity extends AppCompatActivity {

    ListView resultsList;
    ArrayAdapter mArrayAdapter;
    ArrayList<String> resultsArray;
    public static final String DATA_LIST_TILE = "dataListTitle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        resultsList = (ListView) findViewById(R.id.results_list);
        resultsArray = new ArrayList<>();

        // Placeholder elements for ArrayList temporary
        resultsArray.add("Result 1");
        resultsArray.add("Result 2");
        resultsArray.add("Result 3");
        resultsArray.add("Result 4");

        mArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, resultsArray );
        resultsList.setAdapter(mArrayAdapter);
        mArrayAdapter.notifyDataSetChanged();

        // OnItemClickListener for ListView
        resultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent toDetailActivityIntent = new Intent(ResultsActivity.this, DetailActivity.class);
                toDetailActivityIntent.putExtra(DATA_LIST_TILE, resultsArray.get(position));
                startActivity(toDetailActivityIntent);
            }
        });
    }
}
