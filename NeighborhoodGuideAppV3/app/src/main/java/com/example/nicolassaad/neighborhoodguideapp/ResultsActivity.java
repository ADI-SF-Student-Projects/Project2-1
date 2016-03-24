package com.example.nicolassaad.neighborhoodguideapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ResultsActivity extends AppCompatActivity {

    ListView resultsList;
    CursorAdapter mCursorAdapter;
    CursorAdapter mCursorAdapter2;
    CursorAdapter mCursorAdapter3;
    public static final String DATA_LIST_TILE = "dataListTitle";
    Cursor cursorRating;
    Cursor cursorPrice;
    Cursor cursorNames;
    String receivedName;
    float receivedRating;
    String receivedPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        resultsList = (ListView) findViewById(R.id.results_list);
        receivedName = getIntent().getStringExtra(MainActivity.SEARCH_TITLE);
        receivedRating = getIntent().getFloatExtra(MainActivity.SEARCH_RATING, 0.0f);
        receivedPrice = getIntent().getStringExtra(MainActivity.SEARCH_PRICE);

        /**
         * -------------Begin Method invocations---------------
         */
        setCursorForResults();
        /**
         * -------------End Method invocations-----------------
         */

        resultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent toDetailIntent = new Intent(ResultsActivity.this, DetailActivity.class);
                final int receivedCheckbox = getIntent().getIntExtra(MainActivity.CHECK_BOX_KEY, -1);
                if (receivedCheckbox == 1) {
                    cursorNames.moveToPosition(position);
                    toDetailIntent.putExtra(DATA_LIST_TILE, cursorNames.getInt(cursorNames.getColumnIndex(DatabaseHelper.COL_ID)));
                    startActivity(toDetailIntent);
                } else if (receivedCheckbox == 2) {
                    cursorRating.moveToPosition(position);
                    toDetailIntent.putExtra(DATA_LIST_TILE, cursorRating.getInt(cursorRating.getColumnIndex(DatabaseHelper.COL_ID)));
                    startActivity(toDetailIntent);
                } else if (receivedCheckbox == 3) {
                    cursorPrice.moveToPosition(position);
                    toDetailIntent.putExtra(DATA_LIST_TILE, cursorPrice.getInt(cursorPrice.getColumnIndex(DatabaseHelper.COL_ID)));
                    startActivity(toDetailIntent);
                }
            }
        });
    }

    /**
     * setCursorForResults() checks to see which checkbox from MainActivity was checked
     */
    private void setCursorForResults() {
        final int receivedCheckbox = getIntent().getIntExtra(MainActivity.CHECK_BOX_KEY, -1);
        if (receivedCheckbox == 1) {
            cursorNames = DatabaseHelper.getInstance(ResultsActivity.this).searchBarsByName(receivedName);
            if (!cursorNames.moveToFirst()) {
                cursorNames = DatabaseHelper.getInstance(ResultsActivity.this).searchBarsByName(receivedName);
            }
            if (mCursorAdapter == null) {
                String[] columns = new String[]{DatabaseHelper.COL_NAME};
                int[] viewNames = new int[]{android.R.id.text1};
                mCursorAdapter = new SimpleCursorAdapter(ResultsActivity.this, android.R.layout.simple_list_item_1, cursorNames, columns, viewNames, 0);
                resultsList.setAdapter(mCursorAdapter);
            }
        } else if (receivedCheckbox == 2) {
            cursorRating = DatabaseHelper.getInstance(ResultsActivity.this).searchBarsByRating(receivedRating);
            if (!cursorRating.moveToFirst()) {
                cursorRating = DatabaseHelper.getInstance(ResultsActivity.this).searchBarsByRating(receivedRating);
            }
            if (mCursorAdapter == null) {
                String[] columns = new String[]{DatabaseHelper.COL_NAME};
                int[] viewNames = new int[]{android.R.id.text1};
                mCursorAdapter2 = new SimpleCursorAdapter(ResultsActivity.this, android.R.layout.simple_list_item_1, cursorRating, columns, viewNames, 0);
                resultsList.setAdapter(mCursorAdapter2);
            }
        } else if (receivedCheckbox == 3) {
            cursorPrice = DatabaseHelper.getInstance(ResultsActivity.this).searchBarsByPrice(receivedPrice);
            if (!cursorPrice.moveToFirst()) {
                cursorPrice = DatabaseHelper.getInstance(ResultsActivity.this).searchBarsByPrice(receivedPrice);
            }
            if (mCursorAdapter == null && mCursorAdapter2 == null) {
                String[] columns = new String[]{DatabaseHelper.COL_NAME};
                int[] viewNames = new int[]{android.R.id.text1};
                mCursorAdapter3 = new SimpleCursorAdapter(ResultsActivity.this, android.R.layout.simple_list_item_1, cursorPrice, columns, viewNames, 0);
                resultsList.setAdapter(mCursorAdapter3);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Intent toHomeIntent = new Intent(ResultsActivity.this, MainActivity.class);
        startActivity(toHomeIntent);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
