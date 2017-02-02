package com.keerthan.mashape;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LENOVO on 31-01-2017.
 */

public class DBHandler extends SQLiteOpenHelper {
    private static DBHandler mInstance = null;
    private Context mContext;
    public static final String COUNTRY_TABLE_NAME = "contacts";
    public static final String COUNTRY_COLUMN_NAME = "name";
    public static final String COUNTRY_COLUMN_KEY_ID = "id";
    public static final String COUNTRY_COLUMN_POPULATION = "population";
    public static String DBNAME = "CountriesDB.db";
    private static int DBVersion = 1;

    public DBHandler(Context applicationContext) {
        super(applicationContext,DBNAME,null,DBVersion);
    }


    public static DBHandler getInstance(Context context)
    {
        if (mInstance == null){
            mInstance = new DBHandler(context);
        }
        return mInstance;
    }

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+COUNTRY_TABLE_NAME+" ( "+COUNTRY_COLUMN_KEY_ID+" INTEGER PRIMARY KEY,"+COUNTRY_COLUMN_NAME+" TEXT, "+COUNTRY_COLUMN_POPULATION+" TEXT "+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+COUNTRY_TABLE_NAME);
        onCreate(db);
    }

    public void addCountry(Country country)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COUNTRY_COLUMN_NAME,country.getCountryName());
        values.put(COUNTRY_COLUMN_POPULATION,country.getCountryPopulation());

        db.insert(COUNTRY_TABLE_NAME,null,values);
        db.close();
    }

    public List<Country>getAllCountries()
    {
        List<Country>countryList = new ArrayList<>();
        String query = "Select * from "+COUNTRY_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        while (cursor.moveToNext())
        {
            Country country = new Country();
            country.setCountryName(cursor.getString(1));
            country.setCountryPopulation(cursor.getString(2));
            countryList.add(country);
        }
        return countryList;
    }

    public void deleteCountries(int position)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(COUNTRY_TABLE_NAME,COUNTRY_COLUMN_KEY_ID + "= ?", new String[]{String.valueOf(position)});
        db.close();
    }

}
