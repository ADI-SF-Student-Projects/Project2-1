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

// TODO: 3/22/16 WRITE TEST CASES FOR METHODS, FIGURE OUT HOW TO MAKE APP FIT DIFFERENT LAYOUTS (IMAGE RESIZING)
// TODO: 3/22/16 ADD AS MANY HARD CODED STRINGS TO STRINGS.XML
// TODO: 3/22/16 ADD AS MANY HARDCODED DIMENSIONS TO DIMENS.XML, ADD MORE BARS TO LIST!!!!!!!

public class MainActivity extends AppCompatActivity {
    public final String TAG = "MainActivity";
    public static final String SEARCH_TITLE = "searchTitle";
    public static final String SEARCH_RATING = "searchRating";
    public static final String SEARCH_PRICE = "searchPrice";
    public static final String CHECK_BOX_KEY = "checkBoxKey";

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
    private ImageView leftBeer;
    private ImageView rightBeer;

    private static final boolean PREF_KEY_COUNTER_DEFAULT = false;
    private static String PREF_KEY_COUNTER = "data";

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
        toFavListButton = (Button) findViewById(R.id.to_fav_list_button);
        leftBeer = (ImageView) findViewById(R.id.beer_icon_left);
        rightBeer = (ImageView) findViewById(R.id.beer_icon_right);

        /**
         * Adding images to an Array that will be set to cycle through them in an ImageView
         */
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
        /**
         * Method invocations
         */
        setSharedPreferences();
        nextImage();
        startUpMethod();
        setSpinner();
        /**
         * end of method invocations
         */

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
                // Change code to check for radio buttons and ratings bar, get help on how to use them
                if (searchByName.getText().toString().isEmpty() && searchByRatingBar.getRating() == 0 && (!priceRadioLow.isChecked()) && (!priceRadioMid.isChecked()) && (!priceRadioHigh.isChecked())) {
                    Toast.makeText(MainActivity.this, "Please enter some search criteria", Toast.LENGTH_SHORT).show();
                } else {
                    searchByName();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        beerAnimation();
        super.onResume();
    }
    //---------------------End of onCreate method--------------------------

    //---------------------- Begin MainActivity Methods ----------------------

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

    private void setSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (!getSharedPreferences()) {
            populateDatabase();
            editor.putBoolean(PREF_KEY_COUNTER, true);
            editor.apply();
        }
    }

    private boolean getSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        return sharedPreferences.getBoolean(PREF_KEY_COUNTER, PREF_KEY_COUNTER_DEFAULT);
    }

    private void populateDatabase() {
        DatabaseHelper db = DatabaseHelper.getInstance(MainActivity.this);

            db.insert(1, "Missouri Lounge", getString(R.string.missouri_lounge_address), getString(R.string.missouri_lounge_desc), 3.0f, "$", 0, R.drawable.missouriloungeround);
            db.insert(2, "Jupiter", getString(R.string.jupiter_address), getString(R.string.jupiter_desc), 4.0f, "$$$", 0, R.drawable.jupiterround);
            db.insert(3, "Nick's Lounge", getString(R.string.nicks_lounge_address), getString(R.string.nicks_lounge_desc), 3.5f, "$", 0, R.drawable.nicksloungeround);
            db.insert(4, "Hoi Polloi Brewpub", getString(R.string.hoi_polloi_address), getString(R.string.hoi_polloi_desc), 4.5f, "$$", 0, R.drawable.hoipolloiround);
            db.insert(5, "The Graduate", getString(R.string.graduate_address), getString(R.string.graduate_desc), 4.0f, "$$", 0, R.drawable.graduateround);
            db.insert(6, "Moxy", getString(R.string.moxy_address), getString(R.string.moxy_desc), 4.0f, "$", 0, R.drawable.moxyround);
            db.insert(7, "Pappy's Grill", getString(R.string.pappys_grill_address), getString(R.string.pappys_desc), 3.5f, "$$", 0, R.drawable.pappysround);
            db.insert(8, "The Beta Lounge", getString(R.string.beta_lounge_address), getString(R.string.beta_lounge_desc), 3.5f, "$$", 0, R.drawable.betaloungeround);
    }

    /**
     * searchByName() passes an intent that corresponds to which search criteria the user has chosen to use. It also checks
     * to see which radio button was selected and that info is passed into an intent as well.
     */
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
