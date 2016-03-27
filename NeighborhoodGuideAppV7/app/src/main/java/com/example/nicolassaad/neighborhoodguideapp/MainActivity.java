package com.example.nicolassaad.neighborhoodguideapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * This is the starting point for the application. It allows the user to search for different bars by using
 * different search criteria
 */

// TODO: 3/25/16 EACH PLACE IN DETAIL ACTIVITY NEEDS TO HAVE A GOOGLE MAPS LINK
// TODO: 3/25/16 ADD WEBSITE LINK FOR EACH BAR (WILL NEED TO BE AN EXTRA COLUMN IN DATABASE)

public class MainActivity extends AppCompatActivity {
    //region Private Variables
    private int checkboxInput = 1;
    private CheckBox checkBoxName;
    private EditText searchByName;
    private CheckBox checkBoxRating;
    private CheckBox checkBoxPrice;
    private RatingBar searchByRatingBar;
    private RadioGroup priceRadioGroup;
    private RadioButton priceRadioLow;
    private RadioButton priceRadioMid;
    private RadioButton priceRadioHigh;
    private ImageView cycleImg;
    private int[] imageArray;
    private int currentIndex;
    private int startIndex;
    private int endIndex;

    private String nameInput;
    private float ratingInput;
    private Spinner dropdown;

    private Button toFavListButton;
    private Button toMapsButton;
    private ImageView leftBeer;
    private ImageView rightBeer;

    private static final boolean PREF_KEY_COUNTER_DEFAULT = false;
    private static String PREF_KEY_COUNTER = "data";
//endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //region Setting Views
        searchByName = (EditText) findViewById(R.id.search_by_name);
        checkBoxName = (CheckBox) findViewById(R.id.checkbox_name);
        checkBoxRating = (CheckBox) findViewById(R.id.checkbox_rating);
        checkBoxPrice = (CheckBox) findViewById(R.id.checkbox_price);
        searchByRatingBar = (RatingBar) findViewById(R.id.search_ratingbar);
        priceRadioGroup = (RadioGroup) findViewById(R.id.price_radio_group);
        priceRadioLow = (RadioButton) findViewById(R.id.low_price_radio);
        priceRadioMid = (RadioButton) findViewById(R.id.mid_price_radio);
        priceRadioHigh = (RadioButton) findViewById(R.id.high_price_radio);
        toFavListButton = (Button) findViewById(R.id.to_fav_list_button);
        leftBeer = (ImageView) findViewById(R.id.beer_icon_left);
        rightBeer = (ImageView) findViewById(R.id.beer_icon_right);
        toMapsButton = (Button) findViewById(R.id.map_button);
        //endregion

        //region Method Invocations
        /**
         * Method invocations
         */
        addingImagesToArray();
        setSharedPreferences();
        nextImage();
        startUpMethod();
        setSpinner();
        /**
         * end of method invocations
         */
        //endregion

        //region On Click Listeners
        toMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMapsIntent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(toMapsIntent);
            }
        });

        toFavListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toFavsIntent = new Intent(MainActivity.this, FavoritesActivity.class);
                startActivity(toFavsIntent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchByName.getText().toString().isEmpty() && searchByRatingBar.getRating() == 0 && (!priceRadioLow.isChecked()) && (!priceRadioMid.isChecked()) && (!priceRadioHigh.isChecked())) {
                    Toast.makeText(MainActivity.this, "Please enter some search criteria", Toast.LENGTH_SHORT).show();
                } else {
                    searchByCriteria();
                }
            }
        });
        //endregion
    } //---------------------End of onCreate method--------------------------

    //region onResume
    @Override
    protected void onResume() {
        beerAnimation();
        super.onResume();
    }
    //endregion

    //---------------------- Begin MainActivity Methods ----------------------

    //region MainActivity Methods

    /**
     * Adding images to an Array that will be set to cycle through them in an ImageView
     */
    private void addingImagesToArray() {
        cycleImg = (ImageView) findViewById(R.id.cycle_img);
        imageArray = new int[8];
        imageArray[0] = R.drawable.betaloungeround;
        imageArray[1] = R.drawable.hoipolloiround;
        imageArray[2] = R.drawable.graduateround;
        imageArray[3] = R.drawable.jupiterround;
        imageArray[4] = R.drawable.missouriloungeround;
        imageArray[5] = R.drawable.moxyround;
        imageArray[6] = R.drawable.nicksloungeround;
        imageArray[7] = R.drawable.pappysround;
        startIndex = 0;
        endIndex = 7;
    }

    /**
     * beerAnimation() controls the animation for the beer icons
     */
    private void beerAnimation() {
        Animation slideFromLeft = AnimationUtils.loadAnimation(this, R.anim.move);
        leftBeer.startAnimation(slideFromLeft);
        Animation slideFromRight = AnimationUtils.loadAnimation(this, R.anim.move2);
        rightBeer.startAnimation(slideFromRight);
    }

    /**
     * setSpinner populates the drop down menu with items
     */
    private void setSpinner() {
        dropdown = (Spinner) findViewById(R.id.spinner);
        String[] items = new String[]{"Berkeley", "Oakland", "Alameda"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }

    /**
     * nextImage() allows images to cycle forward
     */
    public void nextImage() {
        cycleImg.setImageResource(imageArray[currentIndex]);
        Animation rotateimage = AnimationUtils.loadAnimation(this, R.anim.custom_anim);
        cycleImg.startAnimation(rotateimage);
        currentIndex++;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentIndex > endIndex) {
                    currentIndex--;
                    previousImage();
                } else {
                    nextImage();
                }
            }
        }, 3000); // here 1000(1 second) interval to change from current  to next image
    }

    /**
     * previousImage() allows images to cycle in reverse
     */
    public void previousImage() {
        cycleImg.setImageResource(imageArray[currentIndex]);
        Animation rotateimage = AnimationUtils.loadAnimation(this, R.anim.custom_anim);
        cycleImg.startAnimation(rotateimage);
        currentIndex--;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentIndex < startIndex) {
                    currentIndex++;
                    nextImage();
                } else {
                    previousImage(); // here 1000(1 second) interval to change from current  to previous image
                }
            }
        }, 3000);
    }

    /**
     * Start up methods all the methods that will help to set up the initial state of our MainActivity
     * disableEditTexts sets the initial state of the EditTexts to disabled unless the user checks the corresponding
     * checkbox. GrayOutEditTextBoxes also makes the hint color for the editText boxes gray while they are disabled.
     * getCheckBoxListeners is a method that contains all the invocations for setting up the clickListeners for the
     * Checkboxes.
     */
    private void startUpMethod() {
        disableViews();
        grayOutEditTextBoxes();
        getCheckBoxListeners();
        setPriceCheckBoxListener();
        setRatingCheckBoxListener();
    }

    /**
     * Initially disables all the search views so they can be enabled by checkboxes
     */
    private void disableViews() {
        searchByName.setEnabled(false);
        searchByRatingBar.setEnabled(false);
        priceRadioHigh.setEnabled(false);
        priceRadioMid.setEnabled(false);
        priceRadioLow.setEnabled(false);
    }

    /**
     * Initially grays out the hint in the edit text for searching by name. Hint color is set to black
     * when the name checkbox is checked to inform the user that they can now type in text
     */
    private void grayOutEditTextBoxes() {
        searchByName.setHintTextColor(getResources().getColor(android.R.color.darker_gray));
    }

    /**
     * Calls the method setCheckBoxListeners with the arguments passed in for the searchByCriteria edit
     * text and the checkBoxName checkbox
     */
    private void getCheckBoxListeners() {
        setCheckBoxListeners(searchByName, checkBoxName);
    }

    /**
     * Checks to see if the database has been created and prevents it from being created again
     */
    private void setSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (!getSharedPreferences()) {
            populateDatabase();
            editor.putBoolean(PREF_KEY_COUNTER, true);
            editor.apply();
        }
    }

    /**
     * Gets the shared preferences for the database
     *
     * @return
     */
    private boolean getSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        return sharedPreferences.getBoolean(PREF_KEY_COUNTER, PREF_KEY_COUNTER_DEFAULT);
    }

    private void populateDatabase() {
        ArrayList<BarItem> barItems = PopulateDBItems.getBarItems(this);
        for (BarItem item : barItems) {
            DatabaseHelper.getInstance(this).insert(item.getPrimaryKey(),
                    item.getName(),
                    item.getAddress(),
                    item.getDescription(),
                    item.getRating(),
                    item.getPrice(),
                    item.getFavorite(),
                    item.getImageResourceID());
        }
    }

    /**
     * Passes an intent that corresponds to which search criteria the user has chosen to use. It also checks
     * to see which radio button was selected and that info is passed into an intent as well.
     */
    private void searchByCriteria() {
        Intent searchNameIntent = new Intent(MainActivity.this, ResultsActivity.class);
        nameInput = searchByName.getText().toString();
        ratingInput = searchByRatingBar.getRating();
        String priceLow = "$";
        String priceMid = "$$";
        String priceHigh = "$$$";
        // Passing an intent that tells the Results Activity that checkbox 1 was checked
        if (checkBoxName.isChecked()) {
            checkboxInput = 1;
            searchNameIntent.putExtra(Constants.CHECK_BOX_KEY, checkboxInput);
        } else if (checkBoxRating.isChecked()) {
            // Passing an intent that tells the Results Activity that checkbox 2 was checked
            checkboxInput = 2;
            searchNameIntent.putExtra(Constants.CHECK_BOX_KEY, checkboxInput);
        } else if (checkBoxPrice.isChecked()) {
            // Passing an intent that tells the Results Activity that checkbox 3 was checked
            checkboxInput = 3;
            searchNameIntent.putExtra(Constants.CHECK_BOX_KEY, checkboxInput);
        }
        // Conditional statement that passes an intent based on which radio button was checked
        if (priceRadioLow.isChecked()) {
            searchNameIntent.putExtra(Constants.SEARCH_PRICE, priceLow);
        } else if (priceRadioMid.isChecked()) {
            searchNameIntent.putExtra(Constants.SEARCH_PRICE, priceMid);
        } else {
            searchNameIntent.putExtra(Constants.SEARCH_PRICE, priceHigh);
        }
        searchNameIntent.putExtra(Constants.SEARCH_TITLE, nameInput);
        searchNameIntent.putExtra(Constants.SEARCH_RATING, ratingInput);
        startActivity(searchNameIntent);
    }

    /**
     * Method designed to set multiple checkboxes to enable or disable their corresponding edit text
     * boxes. Currently only used for the checkbox box and edit text for searching by name. But this method
     * makes it easy for new kinds of search criteria to be added if they happen to use an edit text box
     * for searching.
     *
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

    /**
     * Controls the behavior of the price checkbox. If checkbox is checked then the radio buttons
     * are enabled. When the checkbox is unchecked the radio button selections are cleared.
     */
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

    /**
     * Controls the behavior of the rating checkbox. If the checkbox is checked then the rating bar
     * is enabled. If it' unchecked then the rating bar value is cleared
     */
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

    /**
     * Creates the options menu in the action bar
     *
     * @param menu
     * @return
     */
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
    //endregion
}
