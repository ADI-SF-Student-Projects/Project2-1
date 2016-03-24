package com.example.nicolassaad.neighborhoodguideapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuItemImpl;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView addressView;
    TextView descView;
    RatingBar ratingView;
    TextView priceView;
    MenuItemImpl favStar;
    ImageView barImg;
    Button addRemoveFav;
    String favorites;
    DatabaseHelper helper;
    int id;
    ImageView leftBeer2;
    ImageView rightBeer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        addressView = (TextView) findViewById(R.id.detail_address);
        descView = (TextView) findViewById(R.id.detail_desc);
        ratingView = (RatingBar) findViewById(R.id.detail_rating);
        priceView = (TextView) findViewById(R.id.detail_price);
        barImg = (ImageView) findViewById(R.id.bar_img);
        addRemoveFav = (Button) findViewById(R.id.add_remove_fav_button);
        leftBeer2 = (ImageView) findViewById(R.id.beer_icon_left2);
        rightBeer2 = (ImageView) findViewById(R.id.beer_icon_right2);

        helper = DatabaseHelper.getInstance(DetailActivity.this);
        id = getIntent().getIntExtra(ResultsActivity.DATA_LIST_TILE, -1);
        favorites = helper.getFavoritesById(id);

        /**
         * Favorite button setOnClickListener
         */
        addRemoveFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (favorites.equals("1")) {
                    helper.update(id, 0);
                    favorites = "0";
                    setFavorites();
                } else if (favorites.equals("0")) {
                    favorites = "1";
                    helper.update(id, 1);
                    setFavorites();
                }
            }
        });
        /**
         * Begin method invocations for DetailActivity
         */
        setDetails();
        /**
         * end method invocations
         */
    }

    @Override
    protected void onResume() {
        beerAnimation();
        super.onResume();
    }

    private void beerAnimation() {
        Animation slideFromLeft = AnimationUtils.loadAnimation(this, R.anim.move);
        leftBeer2.startAnimation(slideFromLeft);
        Animation slideFromRight = AnimationUtils.loadAnimation(this, R.anim.move2);
        rightBeer2.startAnimation(slideFromRight);
    }
    /**
     * setDetails takes the result that the user clicked on from the ListView in ResultsActivity and
     * sets it to be the title of this activity. Using an intent and database helper, setDetails also
     * sets all the views in this activity to populate them with the correct database data.
     */
    private void setDetails() {

        String name = helper.getNameById(id);
        String address = helper.getAddressById(id);
        String description = helper.getDescriptionById(id);
        String rating = helper.getRatingById(id);
        String price = helper.getPriceById(id);

        Log.d("DetailActivity", "favorite image: " + favorites);
        int image = helper.getImageById(id);

        setTitle(name);
        addressView.setText(address);
        descView.setText(description);
        ratingView.setRating(Float.parseFloat(rating));
        priceView.setText(price);
        barImg.setBackgroundResource(image);
    }
    /**
     * setFavorites() controls how the favorite button and favorite icon behave.
     */
    private void setFavorites() {
        if (favorites.equals("0")) {
            addRemoveFav.setText(" Add Favorite ");
            addRemoveFav.setBackgroundColor(Color.parseColor("#FFDF00"));
            favStar.setVisible(false);
        } else if (favorites.equals("1")) {
            addRemoveFav.setText(" Remove Favorite ");
            addRemoveFav.setBackgroundResource(android.R.color.holo_red_light);
            favStar.setVisible(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        favStar = (MenuItemImpl) menu.findItem(R.id.fav_action_star);

        favStar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent toFavoritesIntent = new Intent(DetailActivity.this, FavoritesActivity.class);
                startActivity(toFavoritesIntent);
                return true;
            }
        });

                setFavorites();
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        Intent toHomeIntent = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(toHomeIntent);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
