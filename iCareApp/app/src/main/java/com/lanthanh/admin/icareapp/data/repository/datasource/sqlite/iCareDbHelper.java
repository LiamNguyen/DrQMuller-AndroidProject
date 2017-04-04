package com.lanthanh.admin.icareapp.data.repository.datasource.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by long.vu on 3/31/2017.
 */

public class iCareDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "icare.db";
    public static final int DATABASE_VERSION = 1;

    public iCareDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLES =
                //Country table
                "CREATE TABLE " + iCareContract.CountryEntry.TABLE_NAME +
                "(" +
                iCareContract.CountryEntry._ID +  " INTEGER PRIMARY KEY, " +
                iCareContract.CountryEntry.COLUMN_COUNTRY_NAME + " TEXT NOT NULL" +
                ");" +
                //City table
                "CREATE TABLE " + iCareContract.CityEntry.TABLE_NAME +
                "(" +
                iCareContract.CityEntry._ID +  " INTEGER PRIMARY KEY, " +
                iCareContract.CityEntry.COLUMN_CITY_NAME + " TEXT NOT NULL, " +
                iCareContract.CityEntry.COLUMN_COUNTRY_ID + " INTEGER, " +
                "FOREIGN KEY(" + iCareContract.CityEntry.COLUMN_COUNTRY_ID + ") REFERENCES " + iCareContract.CountryEntry.TABLE_NAME + "(" + iCareContract.CountryEntry._ID+ ")" +
                ");" +
                //District table
                "CREATE TABLE " + iCareContract.DistrictEntry.TABLE_NAME +
                "(" +
                iCareContract.DistrictEntry._ID +  " INTEGER PRIMARY KEY, " +
                iCareContract.DistrictEntry.COLUMN_DISTRICT_NAME + " TEXT NOT NULL" +
                iCareContract.DistrictEntry.COLUMN_CITY_ID + " INTEGER, " +
                "FOREIGN KEY(" + iCareContract.DistrictEntry.COLUMN_CITY_ID + ") REFERENCES " + iCareContract.CityEntry.TABLE_NAME + "(" + iCareContract.CityEntry._ID+ ")" +
                ");" +
                //Location table
                "CREATE TABLE " + iCareContract.LocationEntry.TABLE_NAME +
                "(" +
                iCareContract.LocationEntry._ID +  " INTEGER PRIMARY KEY, " +
                iCareContract.LocationEntry.COLUMN_LOCATION_NAME + " TEXT NOT NULL" +
                iCareContract.LocationEntry.COLUMN_DISTRICT_ID + " INTEGER, " +
                "FOREIGN KEY(" + iCareContract.LocationEntry.COLUMN_DISTRICT_ID + ") REFERENCES " + iCareContract.DistrictEntry.TABLE_NAME + "(" + iCareContract.DistrictEntry._ID+ ")" +
                ");" +
                //Voucher table
                "CREATE TABLE " + iCareContract.VoucherEntry.TABLE_NAME +
                "(" +
                iCareContract.VoucherEntry._ID +  " INTEGER PRIMARY KEY, " +
                iCareContract.VoucherEntry.COLUMN_VOUCHER_NAME + " TEXT NOT NULL" +
                iCareContract.VoucherEntry.COLUMN_VOUCHER_PRICE + " INTEGER NOT NULL" +
                ");" +
                //Type table
                "CREATE TABLE " + iCareContract.TypeEntry.TABLE_NAME +
                "(" +
                iCareContract.TypeEntry._ID +  " INTEGER PRIMARY KEY, " +
                iCareContract.TypeEntry.COLUMN_TYPE_NAME + " TEXT NOT NULL" +
                ");" +
                //Machine table
                "CREATE TABLE " + iCareContract.MachineEntry.TABLE_NAME +
                "(" +
                iCareContract.MachineEntry._ID +  " INTEGER PRIMARY KEY, " +
                iCareContract.MachineEntry.COLUMN_MACHINE_NAME + " TEXT NOT NULL" +
                iCareContract.MachineEntry.COLUMN_LOCATION_ID + " INTEGER, " +
                "FOREIGN KEY(" + iCareContract.MachineEntry.COLUMN_LOCATION_ID + ") REFERENCES " + iCareContract.LocationEntry.TABLE_NAME + "(" + iCareContract.LocationEntry._ID+ ")" +
                ");" +
                //Time table
                "CREATE TABLE " + iCareContract.TimeEntry.TABLE_NAME +
                "(" +
                iCareContract.TimeEntry._ID +  " INTEGER PRIMARY KEY, " +
                iCareContract.TimeEntry.COLUMN_TIME_NAME + " TEXT NOT NULL" +
                ");" +
                //Eco time table
                "CREATE TABLE " + iCareContract.EcoTimeEntry.TABLE_NAME +
                "(" +
                iCareContract.EcoTimeEntry._ID +  " INTEGER PRIMARY KEY, " +
                iCareContract.EcoTimeEntry.COLUMN_ECOTIME_NAME + " TEXT NOT NULL" +
                ");";

        db.execSQL(SQL_CREATE_TABLES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO Currently, if the db is upgraded then drop and recreate is fine
        final String SQL_DROP_TABLES =
                "DROP TABLE IF EXISTS " + iCareContract.CountryEntry.TABLE_NAME + ";" +
                "DROP TABLE IF EXISTS " + iCareContract.CityEntry.TABLE_NAME + ";" +
                "DROP TABLE IF EXISTS " + iCareContract.DistrictEntry.TABLE_NAME + ";" +
                "DROP TABLE IF EXISTS " + iCareContract.LocationEntry.TABLE_NAME + ";" +
                "DROP TABLE IF EXISTS " + iCareContract.VoucherEntry.TABLE_NAME + ";" +
                "DROP TABLE IF EXISTS " + iCareContract.TypeEntry.TABLE_NAME + ";" +
                "DROP TABLE IF EXISTS " + iCareContract.MachineEntry.TABLE_NAME + ";" +
                "DROP TABLE IF EXISTS " + iCareContract.TimeEntry.TABLE_NAME + ";" +
                "DROP TABLE IF EXISTS " + iCareContract.EcoTimeEntry.TABLE_NAME + ";";
        db.execSQL(SQL_DROP_TABLES);
        onCreate(db);
    }
}
