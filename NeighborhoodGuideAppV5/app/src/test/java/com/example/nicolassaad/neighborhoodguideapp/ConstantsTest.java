package com.example.nicolassaad.neighborhoodguideapp;

import junit.framework.Assert;
import org.junit.Test;

/**
 * jUnit test cases for the Constants class
 * Checks all Keys in class to make sure they are returning the correct strings
 */

public class ConstantsTest {

    @Test
    public void checkDataListTitle() {

        String actualResult = Constants.DATA_LIST_TILE;
        String expectedResult = "dataListTitle";

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void checkCheckboxKey() {

        String actualResult = Constants.CHECK_BOX_KEY;
        String expectedResult = "checkBoxKey";

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void checkSearchPrice() {

        String actualResult = Constants.SEARCH_PRICE;
        String expectedResult = "searchPrice";

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void checkSearchRating() {

        String actualResult = Constants.SEARCH_RATING;
        String expectedResult = "searchRating";

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void checkSearchTitle() {

        String actualResult = Constants.SEARCH_TITLE;
        String expectedResult = "searchTitle";

        Assert.assertEquals(actualResult, expectedResult);
    }
}
