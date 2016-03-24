package com.example.nicolassaad.neighborhoodguideapp;

import android.content.Context;

/**
 * Bar class that stores the method where database info is inserted
 */
public class Bar {

    private Bar() {

    }

    public static void getData(Context context) {
        DatabaseHelper db = DatabaseHelper.getInstance(context);

        db.insert(1, context.getString(R.string.miss_lounge), context.getString(R.string.missouri_lounge_address), context.getString(R.string.missouri_lounge_desc), 3.0f, context.getString(R.string.dollarsign1), 0, R.drawable.missouriloungeround);
        db.insert(2, context.getString(R.string.jupiter), context.getString(R.string.jupiter_address), context.getString(R.string.jupiter_desc), 4.0f, context.getString(R.string.dollarsign3), 0, R.drawable.jupiterround);
        db.insert(3, context.getString(R.string.nick_lounge), context.getString(R.string.nicks_lounge_address), context.getString(R.string.nicks_lounge_desc), 3.5f, context.getString(R.string.dollarsign1), 0, R.drawable.nicksloungeround);
        db.insert(4, context.getString(R.string.hoi_polloi), context.getString(R.string.hoi_polloi_address), context.getString(R.string.hoi_polloi_desc), 4.5f, context.getString(R.string.dollarsign2), 0, R.drawable.hoipolloiround);
        db.insert(5, context.getString(R.string.graduate), context.getString(R.string.graduate_address), context.getString(R.string.graduate_desc), 4.0f, context.getString(R.string.dollarsign2), 0, R.drawable.graduateround);
        db.insert(6, context.getString(R.string.moxy), context.getString(R.string.moxy_address), context.getString(R.string.moxy_desc), 4.0f, context.getString(R.string.dollarsign1), 0, R.drawable.moxyround);
        db.insert(7, context.getString(R.string.pappys_grill), context.getString(R.string.pappys_grill_address), context.getString(R.string.pappys_desc), 3.5f, context.getString(R.string.dollarsign2), 0, R.drawable.pappysround);
        db.insert(8, context.getString(R.string.beta_lounge), context.getString(R.string.beta_lounge_address), context.getString(R.string.beta_lounge_desc), 3.5f, context.getString(R.string.dollarsign2), 0, R.drawable.betaloungeround);

    }
}
