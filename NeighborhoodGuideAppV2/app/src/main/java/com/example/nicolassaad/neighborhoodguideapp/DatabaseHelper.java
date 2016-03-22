package com.example.nicolassaad.neighborhoodguideapp;

/**
 * Created by nicolassaad on 3/16/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "bars.db";
    public static final String TABLE_NAME = "bars";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_ADDRESS = "address";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_RATING = "rating";
    public static final String COL_PRICE = "price";
    public static final String COL_IMG = "image";
    public static final String COL_FAVORITES = "favorites";
    public static final String[] TABLE_COLUMNS = {COL_ID, COL_NAME, COL_ADDRESS, COL_DESCRIPTION, COL_RATING, COL_PRICE, COL_FAVORITES, COL_IMG};

    private static DatabaseHelper instance;

    public static final String SQL_CREATE_BARS_TABLE = "CREATE TABLE " + TABLE_NAME
            + "( "
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NAME + " TEXT, "
            + COL_ADDRESS + " TEXT, "
            + COL_DESCRIPTION + " TEXT, "
            + COL_RATING + " INTEGER, "
            + COL_PRICE + " INTEGER, "
            + COL_FAVORITES + " INTEGER, "
            + COL_IMG + " INTEGER)";

    public void insert(int id, String name, String address, String description, float rating, String price, int favorites, int image) {
        // Get a reference to the database
        SQLiteDatabase db = getWritableDatabase();

        // create a new content value to store values
        ContentValues values = new ContentValues();
        values.put(COL_ID, id);
        values.put(COL_NAME, name);
        values.put(COL_ADDRESS, address);
        values.put(COL_DESCRIPTION, description);
        values.put(COL_RATING, rating);
        values.put(COL_PRICE, price);
        values.put(COL_FAVORITES, favorites);
        values.put(COL_IMG, image);

        // Insert the row into the games table
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void update(int id, int favorites) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_FAVORITES, favorites);

        db.update(TABLE_NAME, values, COL_ID + " = ?", new String[]{id + ""});
    }

    public void delete(int id) {
        // Get a reference to the database
        SQLiteDatabase db = getWritableDatabase();

        // Define the selection, or the where
        String selection = "id = ?";

        // Define the selection values. The ?'s in the selection
        // The number of values in the following array should equal the number of ? in the where clause
        String[] selectionArgs = new String[]{String.valueOf(id)};

        // Delete everything that satisfies the selection
        db.delete(TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_BARS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    public Cursor searchBarsByName(String query) {

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(TABLE_NAME, // a. table
                TABLE_COLUMNS, // b. column names
                COL_NAME + " LIKE ?", // c. selections
                new String[]{"%" + query + "%"}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        return cursor;
    }


//    public Cursor searchBarsByRating(String query) {
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_NAME, // a. table
//                TABLE_COLUMNS, // b. column names
//                COL_RATING + " = ?", // c. selections
//                new String[]{query}, // d. selections args
//                null, // e. group by
//                null, // f. having
//                null, // g. order by
//                null); // h. limit
//        return cursor;
//    }

    public Cursor searchBarsByPrice(String query) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, // a. table
                TABLE_COLUMNS, // b. column names
                COL_PRICE + " = ?", // c. selections
                new String[]{query}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        return cursor;
    }

    public Cursor getBarsList() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, // a. table
                TABLE_COLUMNS, // b. column names
                null, // c. selections
                null, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        return cursor;
    }

    public String getNameById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, TABLE_COLUMNS,
                COL_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex(COL_NAME));
        } else {
            return "No Description Found";
        }
    }

    public String getAddressById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, TABLE_COLUMNS,
                COL_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex(COL_ADDRESS));
        } else {
            return "No Description Found";
        }
    }

    public String getDescriptionById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, TABLE_COLUMNS,
                COL_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION));
        } else {
            return "No Description Found";
        }
    }

    public String getRatingById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, TABLE_COLUMNS,
                COL_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex(COL_RATING));
        } else {
            return "No Description Found";
        }
    }

    public String getPriceById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, TABLE_COLUMNS,
                COL_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex(COL_PRICE));
        } else {
            return "No Description Found";
        }
    }

    public String getFavoritesById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, TABLE_COLUMNS,
                COL_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex(COL_FAVORITES));
        } else {
            return "No Description Found";
        }
    }

    public int getImageById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, TABLE_COLUMNS,
                COL_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex(COL_IMG));
        } else {
            return -1;
        }
    }

    public Cursor searchBarsByRating(float rating) {

        SQLiteDatabase db = this.getReadableDatabase();

        String searchParameters = "";

        if (rating != 0) {
            String strRating = "\'" + String.valueOf(rating) + "\'";

            if (searchParameters != null && !searchParameters.isEmpty()) {
                searchParameters = searchParameters + " AND " + COL_RATING + " = " + strRating;
            } else {
                searchParameters = COL_RATING + " = " + strRating;
            }
        }

        return db.query(TABLE_NAME, TABLE_COLUMNS, searchParameters, null, null, null, null, null);
    }
}
