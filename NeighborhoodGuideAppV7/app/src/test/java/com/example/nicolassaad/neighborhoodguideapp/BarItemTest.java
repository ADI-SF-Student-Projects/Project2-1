package com.example.nicolassaad.neighborhoodguideapp;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by nicolassaad on 3/25/16.
 */
public class BarItemTest {

    @Test
    public void testGetName() {

        BarItem barTest = new BarItem();
        barTest.setName("Missouri Lounge");

        String actualResult = "Missouri Lounge";
        String expectedResult = barTest.getName();

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetAddress() {

        BarItem barTest = new BarItem();
        barTest.setAddress("1763 Alcatraz Ave");

        String actualResult = "1763 Alcatraz Ave";
        String expectedResult = barTest.getAddress();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testGetDescription() {

        BarItem barTest = new BarItem();
        barTest.setDescription("Bustling watering hole with a neon marquee draws a diverse crowd for drink discounts & free popcorn.");

        String actualResult = "Bustling watering hole with a neon marquee draws a diverse crowd for drink discounts & free popcorn.";
        String expectedResult = barTest.getDescription();

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetRating() {

        BarItem barTest = new BarItem();
        barTest.setRating(4.0f);

        float actualResult = 4.0f;
        float expectedResult = barTest.getRating();

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetPrice() {

        BarItem barTest = new BarItem();
        barTest.setPrice("$");

        String actualResult = "$";
        String expectedResult = barTest.getPrice();

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetFavorites() {

        BarItem barTest = new BarItem();
        barTest.setFavorite(0);

        int actualResult = 0;
        int expectedResult = barTest.getFavorite();

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetImageResourceID() {

        BarItem barTest = new BarItem();
        barTest.setImageResourceID(R.drawable.missouriloungeround);

        int actualResult = R.drawable.missouriloungeround;
        int expectedResult = barTest.getImageResourceID();

        Assert.assertEquals(expectedResult, actualResult);
    }
}
