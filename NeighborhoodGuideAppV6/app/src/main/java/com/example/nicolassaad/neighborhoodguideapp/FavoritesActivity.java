package com.example.nicolassaad.neighborhoodguideapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuItemImpl;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class FavoritesActivity extends AppCompatActivity {

    private ListView favList;
    DatabaseHelper helper;
    private CursorAdapter favListCursorAd;
    private Cursor favListCursor;
    private TextView favListEmpty;
    private MenuItemImpl homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favList = (ListView) findViewById(R.id.favorites_list);
        favListEmpty = (TextView) findViewById(R.id.fav_list_is_empty);

        favList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent toDetailIntent = new Intent(FavoritesActivity.this, DetailActivity.class);
                favListCursor.moveToPosition(position);
                toDetailIntent.putExtra(Constants.DATA_LIST_TILE, favListCursor.getInt(favListCursor.getColumnIndex(DatabaseHelper.COL_ID)));
                startActivity(toDetailIntent);
            }
        });
    }
    @Override
    protected void onResume() {
        favListCursor = DatabaseHelper.getInstance(FavoritesActivity.this).searchBarsByFavorites();
        helper = DatabaseHelper.getInstance(FavoritesActivity.this);
        String[] columns = new String[]{DatabaseHelper.COL_NAME};
        int[] viewNames = new int[]{android.R.id.text1};
        favListCursorAd = new SimpleCursorAdapter(FavoritesActivity.this, android.R.layout.simple_list_item_1, favListCursor, columns, viewNames, 0);
        favList.setAdapter(favListCursorAd);

        if (favListCursorAd.isEmpty()) {
            favListEmpty.setVisibility(View.VISIBLE);
        }

        super.onResume();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        homeButton = (MenuItemImpl) menu.findItem(R.id.action_settings);
        homeButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent toHomeIntent = new Intent(FavoritesActivity.this, MainActivity.class);
                startActivity(toHomeIntent);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
