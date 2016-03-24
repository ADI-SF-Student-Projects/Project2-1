package com.example.nicolassaad.neighborhoodguideapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Result Activity contains the list of results that the user will search for
 */
public class ResultsActivity extends AppCompatActivity {

    //region Private Variables
    private ListView resultsList;
    private CursorAdapter mCursorAdapter;
    private CursorAdapter mCursorAdapter2;
    private CursorAdapter mCursorAdapter3;
    private Cursor cursorRating;
    private Cursor cursorPrice;
    private Cursor cursorNames;
    private String receivedName;
    private float receivedRating;
    private String receivedPrice;
    private MenuItemImpl homeButton;
    //endregion Private Variables


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //region Setting Views
        resultsList = (ListView) findViewById(R.id.results_list);
        receivedName = getIntent().getStringExtra(Constants.SEARCH_TITLE);
        receivedRating = getIntent().getFloatExtra(Constants.SEARCH_RATING, 0.0f);
        receivedPrice = getIntent().getStringExtra(Constants.SEARCH_PRICE);
        //endregion

        /**
         * -------------Begin Method invocations---------------
         */
        setCursorForResults();
        onNewIntent(getIntent());
        /**
         * -------------End Method invocations-----------------
         */

        //region On Click Listeners
        resultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent toDetailIntent = new Intent(ResultsActivity.this, DetailActivity.class);
                final int receivedCheckbox = getIntent().getIntExtra(Constants.CHECK_BOX_KEY, -1);
                if (receivedCheckbox == 1) {
                    cursorNames.moveToPosition(position);
                    toDetailIntent.putExtra(Constants.DATA_LIST_TILE, cursorNames.getInt(cursorNames.getColumnIndex(DatabaseHelper.COL_ID)));
                    startActivity(toDetailIntent);
                } else if (receivedCheckbox == 2) {
                    cursorRating.moveToPosition(position);
                    toDetailIntent.putExtra(Constants.DATA_LIST_TILE, cursorRating.getInt(cursorRating.getColumnIndex(DatabaseHelper.COL_ID)));
                    startActivity(toDetailIntent);
                } else if (receivedCheckbox == 3) {
                    cursorPrice.moveToPosition(position);
                    toDetailIntent.putExtra(Constants.DATA_LIST_TILE, cursorPrice.getInt(cursorPrice.getColumnIndex(DatabaseHelper.COL_ID)));
                    startActivity(toDetailIntent);
                }
            }
        });
        //endregion
    } //---------------------End of onCreate method--------------------------
//---------------------- Begin ResultsActivity Methods ----------------------

    //region ResultActivity Methods

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    /**
     * setCursorForResults() checks to see which checkbox from MainActivity was checked
     */
    private void setCursorForResults() {
        final int receivedCheckbox = getIntent().getIntExtra(Constants.CHECK_BOX_KEY, -1);
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
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            String query = intent.getStringExtra(SearchManager.QUERY);
            cursorNames = DatabaseHelper.getInstance(ResultsActivity.this).searchBarsByName(query);
            if (mCursorAdapter != null) {
                mCursorAdapter.swapCursor(cursorNames);
                mCursorAdapter.notifyDataSetChanged();
            } else if (mCursorAdapter2 != null) {
                mCursorAdapter2.swapCursor(cursorNames);
                mCursorAdapter2.notifyDataSetChanged();
            } else if (mCursorAdapter3 != null) {
                mCursorAdapter3.swapCursor(cursorNames);
                mCursorAdapter3.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        Intent toHomeIntent = new Intent(ResultsActivity.this, MainActivity.class);
//        startActivity(toHomeIntent);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion

}
