package com.example.nicolassaad.neighborhoodguideapp;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by nicolassaad on 3/25/16.
 */
public class PopulateDBItems {

    static ArrayList<BarItem> barItems = new ArrayList<>();

    public static ArrayList<BarItem> getBarItems(Context context) {

        BarItem missouriLounge = new BarItem();
        missouriLounge.setPrimaryKey(1);
        missouriLounge.setName(context.getString(R.string.miss_lounge));
        missouriLounge.setAddress(context.getString(R.string.missouri_lounge_address));
        missouriLounge.setDescription(context.getString(R.string.missouri_lounge_desc));
        missouriLounge.setRating(3.0f);
        missouriLounge.setPrice(context.getString(R.string.dollarsign1));
        missouriLounge.setFavorite(0);
        missouriLounge.setImageResourceID(R.drawable.missouriloungeround);
        barItems.add(missouriLounge);

        BarItem jupiter = new BarItem();
        jupiter.setPrimaryKey(2);
        jupiter.setName(context.getString(R.string.jupiter));
        jupiter.setAddress(context.getString(R.string.jupiter_address));
        jupiter.setDescription(context.getString(R.string.jupiter_desc));
        jupiter.setRating(4.0f);
        jupiter.setPrice(context.getString(R.string.dollarsign3));
        jupiter.setFavorite(0);
        jupiter.setImageResourceID(R.drawable.jupiterround);


        BarItem nickslounge = new BarItem();
        nickslounge.setPrimaryKey(3);
        nickslounge.setName(context.getString(R.string.nick_lounge));
        nickslounge.setAddress(context.getString(R.string.nicks_lounge_address));
        nickslounge.setDescription(context.getString(R.string.nicks_lounge_desc));
        nickslounge.setRating(3.5f);
        nickslounge.setPrice(context.getString(R.string.dollarsign1));
        nickslounge.setFavorite(0);
        nickslounge.setImageResourceID(R.drawable.nicksloungeround);
        barItems.add(nickslounge);

        BarItem hoiPolloi = new BarItem();
        hoiPolloi.setPrimaryKey(4);
        hoiPolloi.setName(context.getString(R.string.hoi_polloi));
        hoiPolloi.setAddress(context.getString(R.string.hoi_polloi_address));
        hoiPolloi.setDescription(context.getString(R.string.hoi_polloi_desc));
        hoiPolloi.setRating(4.5f);
        hoiPolloi.setPrice(context.getString(R.string.dollarsign2));
        hoiPolloi.setFavorite(0);
        hoiPolloi.setImageResourceID(R.drawable.hoipolloiround);
        barItems.add(hoiPolloi);


        BarItem graduate = new BarItem();
        graduate.setPrimaryKey(5);
        graduate.setName(context.getString(R.string.graduate));
        graduate.setAddress(context.getString(R.string.graduate));
        graduate.setDescription(context.getString(R.string.graduate_desc));
        graduate.setRating(4.0f);
        graduate.setPrice(context.getString(R.string.dollarsign2));
        graduate.setFavorite(0);
        graduate.setImageResourceID(R.drawable.graduateround);

        BarItem moxy = new BarItem();
        moxy.setPrimaryKey(6);
        moxy.setName(context.getString(R.string.moxy));
        moxy.setAddress(context.getString(R.string.moxy_address));
        moxy.setDescription(context.getString(R.string.moxy_desc));
        moxy.setRating(4.0f);
        moxy.setPrice(context.getString(R.string.dollarsign1));
        moxy.setFavorite(0);
        moxy.setImageResourceID(R.drawable.moxyround);
        barItems.add(moxy);

        BarItem pappysgrill = new BarItem();
        pappysgrill.setPrimaryKey(7);
        pappysgrill.setName(context.getString(R.string.pappys_grill));
        pappysgrill.setAddress(context.getString(R.string.pappys_grill_address));
        pappysgrill.setDescription(context.getString(R.string.pappys_desc));
        pappysgrill.setRating(3.5f);
        pappysgrill.setPrice(context.getString(R.string.dollarsign2));
        pappysgrill.setFavorite(0);
        pappysgrill.setImageResourceID(R.drawable.pappysround);
        barItems.add(pappysgrill);

        BarItem betalounge = new BarItem();
        betalounge.setPrimaryKey(8);
        betalounge.setName(context.getString(R.string.miss_lounge));
        betalounge.setAddress(context.getString(R.string.missouri_lounge_address));
        betalounge.setDescription(context.getString(R.string.missouri_lounge_desc));
        betalounge.setRating(3.0f);
        betalounge.setPrice(context.getString(R.string.dollarsign1));
        betalounge.setFavorite(0);
        betalounge.setImageResourceID(R.drawable.missouriloungeround);
        barItems.add(betalounge);

        return barItems;
    }
}