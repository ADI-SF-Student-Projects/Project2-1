package com.example.nicolassaad.neighborhoodguideapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    public final String TAG = "MainActivity";
    public static final String SEARCH_TITLE = "searchTitle";
    public static final String SEARCH_RATING = "searchRating";
    public static final String SEARCH_PRICE = "searchPrice";
    public static final String CHECK_BOX_KEY = "checkBoxKey";

    int checkboxInput = 1;

    CheckBox checkBoxName;
    EditText searchByName;
    CheckBox checkBoxRating;
    CheckBox checkBoxPrice;
    RatingBar searchByRatingBar;
    RadioGroup priceRadioGroup;
    RadioButton priceRadioLow;
    RadioButton priceRadioMid;
    RadioButton priceRadioHigh;

    String nameInput;
    float ratingInput;
    String priceInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchByName = (EditText) findViewById(R.id.search_by_name);
        checkBoxName = (CheckBox) findViewById(R.id.checkbox_name);
        checkBoxRating = (CheckBox) findViewById(R.id.checkbox_rating);
        checkBoxPrice = (CheckBox) findViewById(R.id.checkbox_price);
        searchByRatingBar = (RatingBar) findViewById(R.id.search_ratingbar);
        priceRadioGroup = (RadioGroup) findViewById(R.id.price_radio_group);
        priceRadioLow = (RadioButton) findViewById(R.id.low_price_radio);
        priceRadioMid = (RadioButton) findViewById(R.id.mid_price_radio);
        priceRadioHigh = (RadioButton) findViewById(R.id.high_price_radio);
        /**
         * Method invocations
         */
        startUpMethod();
        /**
         * end of method invocations
         */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change code to check for radio buttons and ratings bar, get help on how to use them
                if (searchByName.getText().toString().isEmpty() && searchByRatingBar.getRating() == 0 && (!priceRadioLow.isChecked()) && (!priceRadioMid.isChecked()) && (!priceRadioHigh.isChecked())) {
                    Toast.makeText(MainActivity.this, "Please enter some search criteria", Toast.LENGTH_SHORT).show();
                } else {
                    searchByName();
                }
            }
        });
    } //---------------------End of onCreate method--------------------------

    //---------------------- Begin MainActivity Methods ----------------------

    /**
     * Start up methods all the methods that will help to set up the initial state of our MainActivity
     * disableEditTexts sets the initial state of the EditTexts to disabled unless the user checks the corresponding
     * checkbox. GrayOutEditTextBoxes also makes the hint color for the editText boxes gray while they are disabled.
     * getCheckBoxListeners is a method that contains all the invocations for setting up the clickListeners for the
     * Checkboxes.
     */
    private void startUpMethod() {
        populateDatabase();
        disableViews();
        grayOutEditTextBoxes();
        getCheckBoxListeners();
        setPriceCheckBoxListener();
        setRatingCheckBoxListener();
    }
    private void disableViews() {
        searchByName.setEnabled(false);
        searchByRatingBar.setEnabled(false);
        priceRadioHigh.setEnabled(false);
        priceRadioMid.setEnabled(false);
        priceRadioLow.setEnabled(false);
    }

    private void grayOutEditTextBoxes() {
        searchByName.setHintTextColor(getResources().getColor(android.R.color.darker_gray));
    }
    private void getCheckBoxListeners() {
        setCheckBoxListeners(searchByName, checkBoxName);
    }

    private void populateDatabase() {
        DatabaseHelper db = DatabaseHelper.getInstance(MainActivity.this);
        db.insert(1, "Missouri Lounge", getString(R.string.missouri_lounge_address), getString(R.string.missouri_lounge_desc), 3.0f, "$", 0, R.drawable.missourilounge);
        db.insert(2, "Jupiter", getString(R.string.jupiter_address), getString(R.string.jupiter_desc), 4.0f, "$$$", 0, R.drawable.jupiter);
        db.insert(3, "Nick's Lounge", getString(R.string.nicks_lounge_address), getString(R.string.nicks_lounge_desc), 3.5f, "$", 0, R.drawable.nickslounge);
        db.insert(4, "Hoi Polloi Brewpub", getString(R.string.hoi_polloi_address), getString(R.string.hoi_polloi_desc), 4.5f, "$$", 0, R.drawable.hoipolloi);
        db.insert(5, "The Graduate", getString(R.string.graduate_address), getString(R.string.graduate_desc), 4.0f, "$$", 0, R.drawable.graduate);
        db.insert(6, "Moxy", getString(R.string.moxy_address), getString(R.string.moxy_desc), 4.0f, "$", 0, R.drawable.moxy);
        db.insert(7, "Pappy's Grill", getString(R.string.pappys_grill_address), getString(R.string.pappys_desc), 3.5f, "$$", 0, R.drawable.pappys);
        db.insert(8, "The Beta Lounge", getString(R.string.beta_lounge_address), getString(R.string.beta_lounge_desc), 3.5f, "$$", 0, R.drawable.betalounge);
    }

    private void searchByName() {
        Intent searchNameIntent = new Intent(MainActivity.this, ResultsActivity.class);
        nameInput = searchByName.getText().toString();
        ratingInput = searchByRatingBar.getRating();
        String priceLow = "$";
        String priceMid = "$$";
        String priceHigh = "$$$";
        if (checkBoxName.isChecked()) {
            checkboxInput = 1;
            searchNameIntent.putExtra(CHECK_BOX_KEY, checkboxInput);
        } else if (checkBoxRating.isChecked()) {
            checkboxInput = 2;
            searchNameIntent.putExtra(CHECK_BOX_KEY, checkboxInput);
        } else if (checkBoxPrice.isChecked()) {
            checkboxInput = 3;
            searchNameIntent.putExtra(CHECK_BOX_KEY, checkboxInput);
        }

        if (priceRadioLow.isChecked()) {
            searchNameIntent.putExtra(SEARCH_PRICE, priceLow);
        } else if (priceRadioMid.isChecked()) {
            searchNameIntent.putExtra(SEARCH_PRICE, priceMid);
        } else {
            searchNameIntent.putExtra(SEARCH_PRICE, priceHigh);
        }
        searchNameIntent.putExtra(SEARCH_TITLE, nameInput);
        searchNameIntent.putExtra(SEARCH_RATING, ratingInput);
        startActivity(searchNameIntent);
    }

    /**
     * setCheckBoxListeners sets up all three checkBoxes to listen for checks. Once a checkBox is checked
     * the corresponding EditText view will enabled and the text color for the hint will be changed to black.
     * @param text
     * @param checkBox
     */
    private void setCheckBoxListeners(final EditText text, final CheckBox checkBox) {
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    text.setEnabled(true);
                    text.setHintTextColor(getResources().getColor(android.R.color.black));
                } else {
                    text.setEnabled(false);
                    text.setHintTextColor(getResources().getColor(android.R.color.darker_gray));
                    text.setText(null);
                }
            }
        });
    }

    private void setPriceCheckBoxListener() {
        checkBoxPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxPrice.isChecked()) {
                    priceRadioLow.setEnabled(true);
                    priceRadioMid.setEnabled(true);
                    priceRadioHigh.setEnabled(true);
                } else {
                    priceRadioHigh.setEnabled(false);
                    priceRadioMid.setEnabled(false);
                    priceRadioLow.setEnabled(false);
                    priceRadioGroup.clearCheck();
                }
            }
        });
    }

    private void setRatingCheckBoxListener() {
        checkBoxRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxRating.isChecked()) {
                    searchByRatingBar.setEnabled(true);
                } else {
                    searchByRatingBar.setEnabled(false);
                    searchByRatingBar.setRating(0F);
                }
            }
        });
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
