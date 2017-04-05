package com.lanthanh.admin.icareapp.data.repository.datasource.sqlite;

import android.content.ContentValues;
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
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOWeekDay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by long.vu on 4/4/2017.
 */

public class iCareDb {
    private SQLiteDatabase sqliteDb;
    private iCareDbHelper dbHelper;
    private static iCareDb mDb;

    private iCareDb(Context context) {
        dbHelper = new iCareDbHelper(context);
        sqliteDb = dbHelper.getWritableDatabase();
    }

    public static iCareDb getDatabase(Context context) {
        if (mDb == null) {
            mDb = new iCareDb(context);
        }
        return mDb;
    }

    /*
     * CREATE
     */
    public boolean addCoutries(List<DTOCountry> countries) {
        if (countries == null || countries.size() == 0)
            return false;
        for (DTOCountry country: countries) {
            ContentValues cv = new ContentValues();
            cv.put(iCareContract.CountryEntry.COLUMN_COUNTRY_NAME, country.getCountryName());
            cv.put(iCareContract.CountryEntry._ID, country.getCountryId());
            if (sqliteDb.insert(iCareContract.CountryEntry.TABLE_NAME, null, cv) == -1) {
                return false;
            }
        }
        return true;
    }


    public boolean addCities(List<DTOCity> cities, int countryId) {
        if (cities == null || cities.size() == 0)
            return false;
        for (DTOCity city: cities) {
            ContentValues cv = new ContentValues();
            cv.put(iCareContract.CityEntry.COLUMN_CITY_NAME, city.getCityName());
            cv.put(iCareContract.CityEntry._ID, city.getCityId());
            cv.put(iCareContract.CityEntry.COLUMN_COUNTRY_ID, countryId);
            if (sqliteDb.insert(iCareContract.CityEntry.TABLE_NAME, null, cv) == -1) {
                return false;
            }
        }
        return true;
    }

    public boolean addDistricts(List<DTODistrict> districts, int cityId) {
        if (districts == null || districts.size() == 0)
            return false;
        for (DTODistrict district: districts) {
            ContentValues cv = new ContentValues();
            cv.put(iCareContract.DistrictEntry.COLUMN_DISTRICT_NAME, district.getDistrictName());
            cv.put(iCareContract.DistrictEntry._ID, district.getDistrictId());
            cv.put(iCareContract.DistrictEntry.COLUMN_CITY_ID, cityId);
            if (sqliteDb.insert(iCareContract.DistrictEntry.TABLE_NAME, null, cv) == -1) {
                return false;
            }
        }
        return true;
    }

    public boolean addLocations(List<DTOLocation> locations, int districtId) {
        if (locations == null || locations.size() == 0)
            return false;
        for (DTOLocation location: locations) {
            ContentValues cv = new ContentValues();
            cv.put(iCareContract.LocationEntry.COLUMN_LOCATION_NAME, location.getAddress());
            cv.put(iCareContract.LocationEntry._ID, location.getLocationId());
            cv.put(iCareContract.LocationEntry.COLUMN_DISTRICT_ID, districtId);
            if (sqliteDb.insert(iCareContract.LocationEntry.TABLE_NAME, null, cv) == -1) {
                return false;
            }
        }
        return true;
    }

    public boolean addMachines(List<DTOMachine> machines, int locationId) {
        if (machines == null || machines.size() == 0)
            return false;
        for (DTOMachine machine: machines) {
            ContentValues cv = new ContentValues();
            cv.put(iCareContract.MachineEntry.COLUMN_MACHINE_NAME, machine.getMachineName());
            cv.put(iCareContract.MachineEntry._ID, machine.getMachineId());
            cv.put(iCareContract.MachineEntry.COLUMN_LOCATION_ID, locationId);
            if (sqliteDb.insert(iCareContract.MachineEntry.TABLE_NAME, null, cv) == -1) {
                return false;
            }
        }
        return true;
    }

    public boolean addVouchers(List<DTOVoucher> vouchers) {
        if (vouchers == null || vouchers.size() == 0)
            return false;
        for (DTOVoucher voucher: vouchers) {
            ContentValues cv = new ContentValues();
            cv.put(iCareContract.VoucherEntry.COLUMN_VOUCHER_NAME, voucher.getVoucherName());
            cv.put(iCareContract.VoucherEntry.COLUMN_VOUCHER_PRICE, voucher.getVoucherPrice());
            cv.put(iCareContract.VoucherEntry._ID, voucher.getVoucherId());
            if (sqliteDb.insert(iCareContract.VoucherEntry.TABLE_NAME, null, cv) == -1) {
                return false;
            }
        }
        return true;
    }

    public boolean addTypes(List<DTOType> types) {
        if (types == null || types.size() == 0)
            return false;
        for (DTOType type: types) {
            ContentValues cv = new ContentValues();
            cv.put(iCareContract.TypeEntry.COLUMN_TYPE_NAME, type.getTypeName());
            cv.put(iCareContract.TypeEntry._ID, type.getTypeId());
            if (sqliteDb.insert(iCareContract.TypeEntry.TABLE_NAME, null, cv) == -1) {
                return false;
            }
        }
        return true;
    }

    public boolean addTime(List<DTOTime> times) {
        if (times == null || times.size() == 0)
            return false;
        for (DTOTime time: times) {
            ContentValues cv = new ContentValues();
            cv.put(iCareContract.TimeEntry.COLUMN_TIME_NAME, time.getTime());
            cv.put(iCareContract.TimeEntry._ID, time.getTimeId());
            if (sqliteDb.insert(iCareContract.TimeEntry.TABLE_NAME, null, cv) == -1) {
                return false;
            }
        }
        return true;
    }

    public boolean addEcoTime(List<DTOTime> times) {
        if (times == null || times.size() == 0)
            return false;
        for (DTOTime time: times) {
            ContentValues cv = new ContentValues();
            cv.put(iCareContract.EcoTimeEntry.COLUMN_ECOTIME_NAME, time.getTime());
            cv.put(iCareContract.EcoTimeEntry._ID, time.getTimeId());
            if (sqliteDb.insert(iCareContract.EcoTimeEntry.TABLE_NAME, null, cv) == -1) {
                return false;
            }
        }
        return true;
    }

    public boolean addWeekDays(List<DTOWeekDay> weekDays) {
        if (weekDays == null || weekDays.size() == 0)
            return false;
        for (DTOWeekDay weekDay: weekDays) {
            ContentValues cv = new ContentValues();
            cv.put(iCareContract.WeekDayEntry.COLUMN_DAY_NAME, weekDay.getDayName());
            cv.put(iCareContract.WeekDayEntry._ID, weekDay.getDayId());
            if (sqliteDb.insert(iCareContract.WeekDayEntry.TABLE_NAME, null, cv) == -1) {
                return false;
            }
        }
        return true;
    }

    /*
     * READ
     */
    public List<DTOCountry> getCountries() {
        List<DTOCountry> result = new ArrayList<>();
        Cursor cursor =  sqliteDb.query(iCareContract.CountryEntry.TABLE_NAME, null, null, null, null , null, null);
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
        Cursor cursor =  sqliteDb.query(iCareContract.CityEntry.TABLE_NAME, null, iCareContract.CityEntry.COLUMN_COUNTRY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
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
        Cursor cursor =  sqliteDb.query(iCareContract.DistrictEntry.TABLE_NAME, null, iCareContract.DistrictEntry.COLUMN_CITY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
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
        Cursor cursor =  sqliteDb.query(iCareContract.LocationEntry.TABLE_NAME, null, iCareContract.LocationEntry.COLUMN_DISTRICT_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
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
        Cursor cursor =  sqliteDb.query(iCareContract.VoucherEntry.TABLE_NAME, null, null, null, null , null, null);
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
        Cursor cursor =  sqliteDb.query(iCareContract.TypeEntry.TABLE_NAME, null, null, null, null , null, null);
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
        Cursor cursor =  sqliteDb.query(iCareContract.MachineEntry.TABLE_NAME, null, iCareContract.MachineEntry.COLUMN_LOCATION_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
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
        Cursor cursor =  sqliteDb.query(iCareContract.TimeEntry.TABLE_NAME, null, null, null, null , null, null);
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
        Cursor cursor =  sqliteDb.query(iCareContract.EcoTimeEntry.TABLE_NAME, null, null, null, null , null, null);
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

    public List<DTOWeekDay> getWeekDays() {
        List<DTOWeekDay> result = new ArrayList<>();
        Cursor cursor =  sqliteDb.query(iCareContract.WeekDayEntry.TABLE_NAME, null, null, null, null , null, null);
        if (cursor.isFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndex(iCareContract.WeekDayEntry._ID));
                String dayName = cursor.getString(cursor.getColumnIndex(iCareContract.WeekDayEntry.COLUMN_DAY_NAME));
                result.add(new DTOWeekDay(_id, dayName));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }
}
