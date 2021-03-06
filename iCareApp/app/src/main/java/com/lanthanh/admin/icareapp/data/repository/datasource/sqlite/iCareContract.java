package com.lanthanh.admin.icareapp.data.repository.datasource.sqlite;

import android.provider.BaseColumns;

/**
 * Created by long.vu on 3/31/2017.
 */

public class iCareContract {
    private iCareContract(){}

    public static final class CountryEntry implements BaseColumns {
        public static final String TABLE_NAME = "country";
        public static final String COLUMN_COUNTRY_NAME = "countryName";
        public static final String COLUMN_DB_ID = "countryId";
    }

    public static final class CityEntry implements BaseColumns {
        public static final String TABLE_NAME = "city";
        public static final String COLUMN_CITY_NAME = "cityName";
        public static final String COLUMN_DB_ID = "cityId";
        public static final String COLUMN_COUNTRY_ID = "countryId";
    }

    public static final class DistrictEntry implements BaseColumns {
        public static final String TABLE_NAME = "district";
        public static final String COLUMN_DISTRICT_NAME = "districtName";
        public static final String COLUMN_DB_ID = "districtId";
        public static final String COLUMN_CITY_ID = "cityId";
    }

    public static final class LocationEntry implements BaseColumns {
        public static final String TABLE_NAME = "location";
        public static final String COLUMN_LOCATION_NAME = "locationName";
        public static final String COLUMN_DB_ID = "locationId";
        public static final String COLUMN_DISTRICT_ID = "districtId";
    }

    public static final class VoucherEntry implements BaseColumns {
        public static final String TABLE_NAME = "voucher";
        public static final String COLUMN_VOUCHER_NAME = "voucherName";
        public static final String COLUMN_DB_ID = "voucherId";
        public static final String COLUMN_VOUCHER_PRICE = "voucherPrice";
    }

    public static final class TypeEntry implements BaseColumns {
        public static final String TABLE_NAME = "type";
        public static final String COLUMN_TYPE_NAME = "typeName";
        public static final String COLUMN_DB_ID = "typeId";
    }

    public static final class MachineEntry implements BaseColumns {
        public static final String TABLE_NAME = "machine";
        public static final String COLUMN_MACHINE_NAME = "machineName";
        public static final String COLUMN_LOCATION_ID = "locationId";
        public static final String COLUMN_DB_ID = "machineId";
    }

    public static final class TimeEntry implements BaseColumns {
        public static final String TABLE_NAME = "time";
        public static final String COLUMN_TIME_NAME = "timeName";
        public static final String COLUMN_DB_ID = "timeId";
    }

    public static final class EcoTimeEntry implements BaseColumns {
        public static final String TABLE_NAME = "ecotime";
        public static final String COLUMN_ECOTIME_NAME = "ecoTimeName";
        public static final String COLUMN_DB_ID = "ecotimeId";
    }

    public static final class WeekDayEntry implements BaseColumns {
        public static final String TABLE_NAME = "weekday";
        public static final String COLUMN_DAY_NAME = "dayName";
        public static final String COLUMN_DB_ID = "dayId";
    }
}
