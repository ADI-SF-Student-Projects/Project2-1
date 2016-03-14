package com.example.nicolassaad.neighborhoodguideapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    CheckBox checkBoxName;
    EditText searchByName;
    CheckBox checkBoxRating;
    EditText searchByRating;
    CheckBox checkBoxPrice;
    EditText searchByPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checkBoxName = (CheckBox) findViewById(R.id.checkbox_name);
        checkBoxRating = (CheckBox) findViewById(R.id.checkbox_rating);
        checkBoxPrice = (CheckBox) findViewById(R.id.checkbox_price);
        searchByName = (EditText) findViewById(R.id.search_by_name);
        searchByRating = (EditText) findViewById(R.id.search_by_rating);
        searchByPrice = (EditText) findViewById(R.id.search_by_price);

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
                if (searchByName.getText().toString().isEmpty() && searchByRating.getText().toString().isEmpty() &&
                        searchByPrice.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter some search criteria", Toast.LENGTH_SHORT).show();
                } else {
                    Intent toResultsIntent = new Intent(MainActivity.this, ResultsActivity.class);
                    startActivity(toResultsIntent);
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
        disableEditTexts();
        grayOutEditTextBoxes();
        getCheckBoxListeners();
    }
    private void disableEditTexts() {
        searchByName.setEnabled(false);
        searchByRating.setEnabled(false);
        searchByPrice.setEnabled(false);
    }
    private void grayOutEditTextBoxes() {
        searchByName.setHintTextColor(getResources().getColor(android.R.color.darker_gray));
        searchByRating.setHintTextColor(getResources().getColor(android.R.color.darker_gray));
        searchByPrice.setHintTextColor(getResources().getColor(android.R.color.darker_gray));
    }
    private void getCheckBoxListeners() {
        setCheckBoxListeners(searchByName, checkBoxName);
        setCheckBoxListeners(searchByRating, checkBoxRating);
        setCheckBoxListeners(searchByPrice,checkBoxPrice);
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
