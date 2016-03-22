package com.example.nicolassaad.neighborhoodguideapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView addressView;
    TextView descView;
    RatingBar ratingView;
    TextView priceView;
    ImageView favImg;
    ImageView barImg;
    Button addRemoveFav;
    String favorites;
    DatabaseHelper helper;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        addressView = (TextView) findViewById(R.id.detail_address);
        descView = (TextView) findViewById(R.id.detail_desc);
        ratingView = (RatingBar) findViewById(R.id.detail_rating);
        priceView = (TextView) findViewById(R.id.detail_price);
        favImg = (ImageView) findViewById(R.id.fav_img);
        barImg = (ImageView) findViewById(R.id.bar_img);
        addRemoveFav = (Button) findViewById(R.id.add_remove_fav_button);

        helper = DatabaseHelper.getInstance(DetailActivity.this);
        id = getIntent().getIntExtra(ResultsActivity.DATA_LIST_TILE, -1);
        favorites = helper.getFavoritesById(id);


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
        setFavorites();
        /**
         * end method invocations
         */
    }

    /**
     * setTitle takes the result that the user clicked on from the ListView in ResultsActivity and
     * sets it to be the title of this activity.
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

    private void setFavorites() {
        if (favorites.equals("0")) {
            addRemoveFav.setText(" Add Favorite ");
            addRemoveFav.setBackgroundColor(Color.parseColor("#FFDF00"));
            favImg.setVisibility(View.INVISIBLE);
        } else if (favorites.equals("1")) {
            addRemoveFav.setText(" Remove Favorite ");
            addRemoveFav.setBackgroundResource(android.R.color.holo_red_light);
            favImg.setVisibility(View.VISIBLE);
        }
    }
}
