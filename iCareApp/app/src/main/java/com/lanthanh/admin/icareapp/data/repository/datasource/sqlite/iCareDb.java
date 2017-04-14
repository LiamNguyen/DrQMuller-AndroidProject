package com.lanthanh.admin.icareapp.data.repository.datasource.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
        try {
            if (countries == null || countries.size() == 0)
                return false;
            sqliteDb.beginTransaction();
            String SQL_INSERT = "INSERT INTO " + iCareContract.CountryEntry.TABLE_NAME + " (" + iCareContract.CountryEntry.COLUMN_DB_ID + ", " + iCareContract.CountryEntry.COLUMN_COUNTRY_NAME + ") " +
                                "VALUES ";
            for (DTOCountry country : countries) {
                SQL_INSERT += "(" + country.getCountryId() + ", \"" + country.getCountryName() + "\")";
                if (countries.indexOf(country) + 1 < countries.size())
                    SQL_INSERT += ",";
            }
            SQL_INSERT += ";";
            sqliteDb.execSQL(SQL_INSERT);
            sqliteDb.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage());
        } finally {
            sqliteDb.endTransaction();
        }
        return true;
    }


    public boolean addCities(List<DTOCity> cities, int countryId) {
        try {
            if (cities == null || cities.size() == 0)
                return false;
            sqliteDb.beginTransaction();
            String SQL_INSERT = "INSERT INTO " +
                                iCareContract.CityEntry.TABLE_NAME + " (" + iCareContract.CityEntry.COLUMN_DB_ID + ", " + iCareContract.CityEntry.COLUMN_CITY_NAME + ", " +  iCareContract.CityEntry.COLUMN_COUNTRY_ID + ") " +
                                "VALUES ";
            for (DTOCity city : cities) {
                SQL_INSERT += "(" + city.getCityId() + ", \"" + city.getCityName() + "\", " + countryId + ")";
                if (cities.indexOf(city) + 1 < cities.size())
                    SQL_INSERT += ",";
            }
            SQL_INSERT += ";";
            sqliteDb.execSQL(SQL_INSERT);
            sqliteDb.setTransactionSuccessful();
        }catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage());
        } finally {
            sqliteDb.endTransaction();
        }
        return true;
    }

    public boolean addDistricts(List<DTODistrict> districts, int cityId) {
        try {
            if (districts == null || districts.size() == 0)
                return false;
            sqliteDb.beginTransaction();
            String SQL_INSERT = "INSERT INTO " +
                    iCareContract.DistrictEntry.TABLE_NAME + " (" + iCareContract.DistrictEntry.COLUMN_DB_ID + ", " + iCareContract.DistrictEntry.COLUMN_DISTRICT_NAME + ", " +  iCareContract.DistrictEntry.COLUMN_CITY_ID + ") " +
                    "VALUES ";
            for (DTODistrict district: districts) {
                SQL_INSERT += "(" + district.getDistrictId() + ", \"" + district.getDistrictName() + "\", " + cityId + ")";
                if (districts.indexOf(district) + 1 < districts.size())
                    SQL_INSERT += ",";
            }
            SQL_INSERT += ";";
            sqliteDb.execSQL(SQL_INSERT);
            sqliteDb.setTransactionSuccessful();
        }catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage());
        } finally {
            sqliteDb.endTransaction();
        }
        return true;
    }

    public boolean addLocations(List<DTOLocation> locations, int districtId) {
        try {
            if (locations == null || locations.size() == 0)
                return false;
            sqliteDb.beginTransaction();
            String SQL_INSERT = "INSERT INTO " +
                    iCareContract.LocationEntry.TABLE_NAME + " (" + iCareContract.LocationEntry.COLUMN_DB_ID + ", " + iCareContract.LocationEntry.COLUMN_LOCATION_NAME + ", " +  iCareContract.LocationEntry.COLUMN_DISTRICT_ID + ") " +
                    "VALUES ";
            for (DTOLocation location: locations) {
                SQL_INSERT += "(" + location.getLocationId() + ", \"" + location.getAddress() + "\", " + districtId + ")";
                if (locations.indexOf(location) + 1 < locations.size())
                    SQL_INSERT += ",";
            }
            SQL_INSERT += ";";
            sqliteDb.execSQL(SQL_INSERT);
            sqliteDb.setTransactionSuccessful();
        }catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage());
        } finally {
            sqliteDb.endTransaction();
        }
        return true;
    }

    public boolean addMachines(List<DTOMachine> machines, int locationId) {
        try {
            if (machines == null || machines.size() == 0)
                return false;
            sqliteDb.beginTransaction();
            String SQL_INSERT = "INSERT INTO " +
                    iCareContract.MachineEntry.TABLE_NAME + " (" + iCareContract.MachineEntry.COLUMN_DB_ID + ", " + iCareContract.MachineEntry.COLUMN_MACHINE_NAME + ", " +  iCareContract.MachineEntry.COLUMN_LOCATION_ID + ") " +
                    "VALUES ";
            for (DTOMachine machine: machines) {
                SQL_INSERT += "(" + machine.getMachineId() + ", \"" + machine.getMachineName() + "\", " + locationId + ")";
                if (machines.indexOf(machine) + 1 < machines.size())
                    SQL_INSERT += ",";
            }
            SQL_INSERT += ";";
            sqliteDb.execSQL(SQL_INSERT);
            sqliteDb.setTransactionSuccessful();
        }catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage());
        } finally {
            sqliteDb.endTransaction();
        }
        return true;
    }

    public boolean addVouchers(List<DTOVoucher> vouchers) {
        try {
            if (vouchers == null || vouchers.size() == 0)
                return false;
            sqliteDb.beginTransaction();
            String SQL_INSERT = "INSERT INTO " +
                    iCareContract.VoucherEntry.TABLE_NAME + " (" + iCareContract.VoucherEntry.COLUMN_DB_ID + ", " + iCareContract.VoucherEntry.COLUMN_VOUCHER_NAME + ", " +  iCareContract.VoucherEntry.COLUMN_VOUCHER_PRICE + ") " +
                    "VALUES ";
            for (DTOVoucher voucher: vouchers) {
                SQL_INSERT += "(" + voucher.getVoucherId() + ", \"" + voucher.getVoucherName() + "\", " + voucher.getVoucherPrice() + ")";
                if (vouchers.indexOf(voucher) + 1 < vouchers.size())
                    SQL_INSERT += ",";
            }
            SQL_INSERT += ";";
            sqliteDb.execSQL(SQL_INSERT);
            sqliteDb.setTransactionSuccessful();
        }catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage());
        } finally {
            sqliteDb.endTransaction();
        }
        return true;
    }

    public boolean addTypes(List<DTOType> types) {
        try {
            if (types == null || types.size() == 0)
                return false;
            sqliteDb.beginTransaction();
            String SQL_INSERT = "INSERT INTO " + iCareContract.TypeEntry.TABLE_NAME + " (" + iCareContract.TypeEntry.COLUMN_DB_ID + ", " + iCareContract.TypeEntry.COLUMN_TYPE_NAME + ") " +
                                "VALUES ";
            for (DTOType type: types) {
                SQL_INSERT += "(" + type.getTypeId() + ", \"" + type.getTypeName() + "\")";
                if (types.indexOf(type) + 1 < types.size())
                    SQL_INSERT += ",";
            }
            SQL_INSERT += ";";
            sqliteDb.execSQL(SQL_INSERT);
            sqliteDb.setTransactionSuccessful();
        }catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage());
        } finally {
            sqliteDb.endTransaction();
        }
        return true;
    }

    public boolean addTime(List<DTOTime> times) {
        try {
            if (times == null || times.size() == 0)
                return false;
            sqliteDb.beginTransaction();
            String SQL_INSERT = "INSERT INTO " + iCareContract.TimeEntry.TABLE_NAME + " (" + iCareContract.TimeEntry.COLUMN_DB_ID + ", " + iCareContract.TimeEntry.COLUMN_TIME_NAME + ") " +
                                "VALUES ";
            for (DTOTime time: times) {
                SQL_INSERT += "(" + time.getTimeId() + ", \"" + time.getTime() + "\")";
                if (times.indexOf(time) + 1 < times.size())
                    SQL_INSERT += ",";
            }
            SQL_INSERT += ";";
            sqliteDb.execSQL(SQL_INSERT);
            sqliteDb.setTransactionSuccessful();
        }catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage());
        } finally {
            sqliteDb.endTransaction();
        }
        return true;
    }

    public boolean addEcoTime(List<DTOTime> times) {
        try {
            if (times == null || times.size() == 0)
                return false;
            sqliteDb.beginTransaction();
            String SQL_INSERT = "INSERT INTO " + iCareContract.EcoTimeEntry.TABLE_NAME + " (" + iCareContract.EcoTimeEntry.COLUMN_DB_ID + ", " + iCareContract.EcoTimeEntry.COLUMN_ECOTIME_NAME + ") " +
                                "VALUES ";
            for (DTOTime time: times) {
                SQL_INSERT += "(" + time.getTimeId() + ", \"" + time.getTime() + "\")";
                if (times.indexOf(time) + 1 < times.size())
                    SQL_INSERT += ",";
            }
            SQL_INSERT += ";";
            sqliteDb.execSQL(SQL_INSERT);
            sqliteDb.setTransactionSuccessful();
        }catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage());
        } finally {
            sqliteDb.endTransaction();
        }
        return true;
    }

    public boolean addWeekDays(List<DTOWeekDay> weekDays) {
        try {
            if (weekDays == null || weekDays.size() == 0)
                return false;
            sqliteDb.beginTransaction();
            String SQL_INSERT = "INSERT INTO " + iCareContract.WeekDayEntry.TABLE_NAME + " (" + iCareContract.WeekDayEntry.COLUMN_DB_ID + ", " + iCareContract.WeekDayEntry.COLUMN_DAY_NAME + ") " +
                                "VALUES ";
            for (DTOWeekDay weekDay: weekDays) {
                SQL_INSERT += "(" + weekDay.getDayId() + ", \"" + weekDay.getDayName() + "\")";
                if (weekDays.indexOf(weekDay) + 1 < weekDays.size())
                    SQL_INSERT += ",";
            }
            SQL_INSERT += ";";
            sqliteDb.execSQL(SQL_INSERT);
            sqliteDb.setTransactionSuccessful();
        }catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage());
        } finally {
            sqliteDb.endTransaction();
        }
        return true;
    }

    /*
     * READ
     */
    public List<DTOCountry> getCountries() {
        List<DTOCountry> result = new ArrayList<>();
        Cursor cursor =  sqliteDb.query(iCareContract.CountryEntry.TABLE_NAME, null, null, null, null , null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(iCareContract.CountryEntry.COLUMN_DB_ID));
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
        if (cursor.moveToFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndex(iCareContract.CityEntry.COLUMN_DB_ID));
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
        if (cursor.moveToFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndex(iCareContract.DistrictEntry.COLUMN_DB_ID));
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
        if (cursor.moveToFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndex(iCareContract.LocationEntry.COLUMN_DB_ID));
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
        if (cursor.moveToFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndex(iCareContract.VoucherEntry.COLUMN_DB_ID));
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
        if (cursor.moveToFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndex(iCareContract.TypeEntry.COLUMN_DB_ID));
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
        if (cursor.moveToFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndex(iCareContract.MachineEntry.COLUMN_DB_ID));
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
        if (cursor.moveToFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndex(iCareContract.TimeEntry.COLUMN_DB_ID));
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
        if (cursor.moveToFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndex(iCareContract.EcoTimeEntry.COLUMN_DB_ID));
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
        if (cursor.moveToFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndex(iCareContract.WeekDayEntry.COLUMN_DB_ID));
                String dayName = cursor.getString(cursor.getColumnIndex(iCareContract.WeekDayEntry.COLUMN_DAY_NAME));
                result.add(new DTOWeekDay(_id, dayName));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }
}
