package com.lanthanh.admin.icareapp.data.repository.datasource.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lanthanh.admin.icareapp.presentation.model.dto.DTOCity;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOCountry;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTODistrict;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOLocation;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOMachine;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOTime;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOType;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOVoucher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by long.vu on 4/4/2017.
 */

public class iCareDb {
    private SQLiteDatabase mDb;
    private iCareDbHelper dbHelper;

    public iCareDb(Context context) {
        dbHelper = new iCareDbHelper(context);
        mDb = dbHelper.getWritableDatabase();
    }

    public List<DTOCountry> getCountries() {
        List<DTOCountry> result = new ArrayList<>();
        Cursor cursor =  mDb.query(iCareContract.CountryEntry.TABLE_NAME, null, null, null, null , null, null);
        if (cursor.isFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(iCareContract.CountryEntry._ID));
                String countryName = cursor.getString(cursor.getColumnIndex(iCareContract.CountryEntry.COLUMN_COUNTRY_NAME));
                result.add(new DTOCountry(id, countryName));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public List<DTOCity> getCitiesByCountryId(int id) {
        List<DTOCity> result = new ArrayList<>();
        Cursor cursor =  mDb.query(iCareContract.CityEntry.TABLE_NAME, null, iCareContract.CityEntry.COLUMN_COUNTRY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.isFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndex(iCareContract.CityEntry._ID));
                String cityName = cursor.getString(cursor.getColumnIndex(iCareContract.CityEntry.COLUMN_CITY_NAME));
                result.add(new DTOCity(_id, cityName));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public List<DTODistrict> getDistrictsByCityId(int id) {
        List<DTODistrict> result = new ArrayList<>();
        Cursor cursor =  mDb.query(iCareContract.DistrictEntry.TABLE_NAME, null, iCareContract.DistrictEntry.COLUMN_CITY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.isFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndex(iCareContract.DistrictEntry._ID));
                String districtName = cursor.getString(cursor.getColumnIndex(iCareContract.DistrictEntry.COLUMN_DISTRICT_NAME));
                result.add(new DTODistrict(_id, districtName));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public List<DTOLocation> getLocationsByDistrictId(int id) {
        List<DTOLocation> result = new ArrayList<>();
        Cursor cursor =  mDb.query(iCareContract.LocationEntry.TABLE_NAME, null, iCareContract.LocationEntry.COLUMN_DISTRICT_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.isFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndex(iCareContract.LocationEntry._ID));
                String locationName = cursor.getString(cursor.getColumnIndex(iCareContract.LocationEntry.COLUMN_LOCATION_NAME));
                result.add(new DTOLocation(_id, locationName));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public List<DTOVoucher> getVouchers() {
        List<DTOVoucher> result = new ArrayList<>();
        Cursor cursor =  mDb.query(iCareContract.VoucherEntry.TABLE_NAME, null, null, null, null , null, null);
        if (cursor.isFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndex(iCareContract.VoucherEntry._ID));
                String voucherName = cursor.getString(cursor.getColumnIndex(iCareContract.VoucherEntry.COLUMN_VOUCHER_NAME));
                int voucherPrice = cursor.getInt(cursor.getColumnIndex(iCareContract.VoucherEntry.COLUMN_VOUCHER_PRICE));
                result.add(new DTOVoucher(_id, voucherName, voucherPrice));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public List<DTOType> getTypes() {
        List<DTOType> result = new ArrayList<>();
        Cursor cursor =  mDb.query(iCareContract.TypeEntry.TABLE_NAME, null, null, null, null , null, null);
        if (cursor.isFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndex(iCareContract.TypeEntry._ID));
                String typeName = cursor.getString(cursor.getColumnIndex(iCareContract.TypeEntry.COLUMN_TYPE_NAME));
                result.add(new DTOType(_id, typeName));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public List<DTOMachine> getMachinesByLocationId(int id) {
        List<DTOMachine> result = new ArrayList<>();
        Cursor cursor =  mDb.query(iCareContract.MachineEntry.TABLE_NAME, null, iCareContract.MachineEntry.COLUMN_LOCATION_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.isFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndex(iCareContract.MachineEntry._ID));
                String machineName = cursor.getString(cursor.getColumnIndex(iCareContract.MachineEntry.COLUMN_MACHINE_NAME));
                result.add(new DTOMachine(_id, machineName));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public List<DTOTime> getAllTime() {
        List<DTOTime> result = new ArrayList<>();
        Cursor cursor =  mDb.query(iCareContract.TimeEntry.TABLE_NAME, null, null, null, null , null, null);
        if (cursor.isFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndex(iCareContract.TimeEntry._ID));
                String timeName = cursor.getString(cursor.getColumnIndex(iCareContract.TimeEntry.COLUMN_TIME_NAME));
                result.add(new DTOTime(_id, timeName));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public List<DTOTime> getEcoTime() {
        List<DTOTime> result = new ArrayList<>();
        Cursor cursor =  mDb.query(iCareContract.EcoTimeEntry.TABLE_NAME, null, null, null, null , null, null);
        if (cursor.isFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndex(iCareContract.EcoTimeEntry._ID));
                String timeName = cursor.getString(cursor.getColumnIndex(iCareContract.EcoTimeEntry.COLUMN_ECOTIME_NAME));
                result.add(new DTOTime(_id, timeName));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }
}
