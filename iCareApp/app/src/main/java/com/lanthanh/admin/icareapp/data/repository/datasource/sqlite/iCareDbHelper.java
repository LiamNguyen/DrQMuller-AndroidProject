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
        final String SQL_CREATE_TABLE_COUNTRY =
                "CREATE TABLE " + iCareContract.CountryEntry.TABLE_NAME +
                "(" +
                iCareContract.CountryEntry._ID +  " INTEGER PRIMARY KEY, " +
                iCareContract.CountryEntry.COLUMN_COUNTRY_NAME + " TEXT NOT NULL, " +
                iCareContract.CountryEntry.COLUMN_DB_ID + " INTEGER UNIQUE" +
                ");";
        final String SQL_CREATE_TABLE_CITY =
                "CREATE TABLE " + iCareContract.CityEntry.TABLE_NAME +
                "(" +
                iCareContract.CityEntry._ID +  " INTEGER PRIMARY KEY, " +
                iCareContract.CityEntry.COLUMN_CITY_NAME + " TEXT NOT NULL, " +
                iCareContract.CityEntry.COLUMN_COUNTRY_ID + " INTEGER, " +
                iCareContract.CityEntry.COLUMN_DB_ID + " INTEGER UNIQUE, " +
                "FOREIGN KEY(" + iCareContract.CityEntry.COLUMN_COUNTRY_ID + ") REFERENCES " + iCareContract.CountryEntry.TABLE_NAME + "(" + iCareContract.CountryEntry.COLUMN_DB_ID+ ")" +
                ");";
        final String SQL_CREATE_TABLE_DISTRICT =
                "CREATE TABLE " + iCareContract.DistrictEntry.TABLE_NAME +
                "(" +
                iCareContract.DistrictEntry._ID +  " INTEGER PRIMARY KEY, " +
                iCareContract.DistrictEntry.COLUMN_DISTRICT_NAME + " TEXT NOT NULL, " +
                iCareContract.DistrictEntry.COLUMN_CITY_ID + " INTEGER, " +
                iCareContract.DistrictEntry.COLUMN_DB_ID + " INTEGER UNIQUE, " +
                "FOREIGN KEY(" + iCareContract.DistrictEntry.COLUMN_CITY_ID + ") REFERENCES " + iCareContract.CityEntry.TABLE_NAME + "(" + iCareContract.CityEntry.COLUMN_DB_ID+ ")" +
                ");";
        final String SQL_CREATE_TABLE_LOCATION =
                "CREATE TABLE " + iCareContract.LocationEntry.TABLE_NAME +
                "(" +
                iCareContract.LocationEntry._ID +  " INTEGER PRIMARY KEY, " +
                iCareContract.LocationEntry.COLUMN_LOCATION_NAME + " TEXT NOT NULL, " +
                iCareContract.LocationEntry.COLUMN_DISTRICT_ID + " INTEGER, " +
                iCareContract.LocationEntry.COLUMN_DB_ID + " INTEGER UNIQUE, " +
                "FOREIGN KEY(" + iCareContract.LocationEntry.COLUMN_DISTRICT_ID + ") REFERENCES " + iCareContract.DistrictEntry.TABLE_NAME + "(" + iCareContract.DistrictEntry.COLUMN_DB_ID+ ")" +
                ");";
        final String SQL_CREATE_TABLE_VOUCHER =
                "CREATE TABLE " + iCareContract.VoucherEntry.TABLE_NAME +
                "(" +
                iCareContract.VoucherEntry._ID +  " INTEGER PRIMARY KEY, " +
                iCareContract.VoucherEntry.COLUMN_VOUCHER_NAME + " TEXT NOT NULL, " +
                iCareContract.VoucherEntry.COLUMN_VOUCHER_PRICE + " INTEGER NOT NULL, " +
                iCareContract.VoucherEntry.COLUMN_DB_ID + " INTEGER UNIQUE" +
                ");";
        final String SQL_CREATE_TABLE_TYPE =
                "CREATE TABLE " + iCareContract.TypeEntry.TABLE_NAME +
                "(" +
                iCareContract.TypeEntry._ID +  " INTEGER PRIMARY KEY, " +
                iCareContract.TypeEntry.COLUMN_TYPE_NAME + " TEXT NOT NULL, " +
                iCareContract.TypeEntry.COLUMN_DB_ID + " INTEGER UNIQUE" +
                ");";
        final String SQL_CREATE_TABLE_MACHINE =
                "CREATE TABLE " + iCareContract.MachineEntry.TABLE_NAME +
                "(" +
                iCareContract.MachineEntry._ID +  " INTEGER PRIMARY KEY, " +
                iCareContract.MachineEntry.COLUMN_MACHINE_NAME + " TEXT NOT NULL, " +
                iCareContract.MachineEntry.COLUMN_LOCATION_ID + " INTEGER, " +
                iCareContract.MachineEntry.COLUMN_DB_ID + " INTEGER UNIQUE, " +
                "FOREIGN KEY(" + iCareContract.MachineEntry.COLUMN_LOCATION_ID + ") REFERENCES " + iCareContract.LocationEntry.TABLE_NAME + "(" + iCareContract.LocationEntry.COLUMN_DB_ID+ ")" +
                ");";
        final String SQL_CREATE_TABLE_TIME =
                "CREATE TABLE " + iCareContract.TimeEntry.TABLE_NAME +
                "(" +
                iCareContract.TimeEntry._ID +  " INTEGER PRIMARY KEY, " +
                iCareContract.TimeEntry.COLUMN_TIME_NAME + " TEXT NOT NULL, " +
                iCareContract.TimeEntry.COLUMN_DB_ID + " INTEGER UNIQUE" +
                ");";
        final String SQL_CREATE_TABLE_ECOTIME =
                "CREATE TABLE " + iCareContract.EcoTimeEntry.TABLE_NAME +
                "(" +
                iCareContract.EcoTimeEntry._ID +  " INTEGER PRIMARY KEY, " +
                iCareContract.EcoTimeEntry.COLUMN_ECOTIME_NAME + " TEXT NOT NULL, " +
                iCareContract.EcoTimeEntry.COLUMN_DB_ID + " INTEGER UNIQUE" +
                ");";
        final String SQL_CREATE_TABLE_WEEKDAY =
                "CREATE TABLE " + iCareContract.WeekDayEntry.TABLE_NAME +
                "(" +
                iCareContract.WeekDayEntry._ID +  " INTEGER PRIMARY KEY, " +
                iCareContract.WeekDayEntry.COLUMN_DAY_NAME + " TEXT NOT NULL, " +
                iCareContract.WeekDayEntry.COLUMN_DB_ID + " INTEGER UNIQUE" +
                ");";

        db.execSQL(SQL_CREATE_TABLE_COUNTRY);
        db.execSQL(SQL_CREATE_TABLE_CITY);
        db.execSQL(SQL_CREATE_TABLE_DISTRICT);
        db.execSQL(SQL_CREATE_TABLE_LOCATION);
        db.execSQL(SQL_CREATE_TABLE_VOUCHER);
        db.execSQL(SQL_CREATE_TABLE_TYPE);
        db.execSQL(SQL_CREATE_TABLE_MACHINE);
        db.execSQL(SQL_CREATE_TABLE_TIME);
        db.execSQL(SQL_CREATE_TABLE_ECOTIME);
        db.execSQL(SQL_CREATE_TABLE_WEEKDAY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO Currently, if the db is upgraded then drop and recreate is fine
        final String SQL_DROP_TABLE_COUNTRY = "DROP TABLE IF EXISTS " + iCareContract.CountryEntry.TABLE_NAME + ";";
        final String SQL_DROP_TABLE_CITY = "DROP TABLE IF EXISTS " + iCareContract.CityEntry.TABLE_NAME + ";";
        final String SQL_DROP_TABLE_DISTRICT = "DROP TABLE IF EXISTS " + iCareContract.DistrictEntry.TABLE_NAME + ";";
        final String SQL_DROP_TABLE_LOCATION = "DROP TABLE IF EXISTS " + iCareContract.LocationEntry.TABLE_NAME + ";";
        final String SQL_DROP_TABLE_VOUCHER = "DROP TABLE IF EXISTS " + iCareContract.VoucherEntry.TABLE_NAME + ";";
        final String SQL_DROP_TABLE_TYPE = "DROP TABLE IF EXISTS " + iCareContract.TypeEntry.TABLE_NAME + ";";
        final String SQL_DROP_TABLE_MACHINE = "DROP TABLE IF EXISTS " + iCareContract.MachineEntry.TABLE_NAME + ";";
        final String SQL_DROP_TABLE_TIME = "DROP TABLE IF EXISTS " + iCareContract.TimeEntry.TABLE_NAME + ";";
        final String SQL_DROP_TABLE_ECOTIME = "DROP TABLE IF EXISTS " + iCareContract.EcoTimeEntry.TABLE_NAME + ";";
        final String SQL_DROP_TABLE_WEEKDAY = "DROP TABLE IF EXISTS " + iCareContract.WeekDayEntry.TABLE_NAME + ";";
        db.execSQL(SQL_DROP_TABLE_COUNTRY);
        db.execSQL(SQL_DROP_TABLE_CITY);
        db.execSQL(SQL_DROP_TABLE_DISTRICT);
        db.execSQL(SQL_DROP_TABLE_LOCATION);
        db.execSQL(SQL_DROP_TABLE_VOUCHER);
        db.execSQL(SQL_DROP_TABLE_TYPE);
        db.execSQL(SQL_DROP_TABLE_MACHINE);
        db.execSQL(SQL_DROP_TABLE_TIME);
        db.execSQL(SQL_DROP_TABLE_ECOTIME);
        db.execSQL(SQL_DROP_TABLE_WEEKDAY);
        onCreate(db);
    }
}
