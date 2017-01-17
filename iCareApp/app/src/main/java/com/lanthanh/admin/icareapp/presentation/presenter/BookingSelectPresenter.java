package com.lanthanh.admin.icareapp.presentation.presenter;

import com.lanthanh.admin.icareapp.data.manager.CityManager;
import com.lanthanh.admin.icareapp.data.manager.CountryManager;
import com.lanthanh.admin.icareapp.data.manager.DistrictManager;
import com.lanthanh.admin.icareapp.data.manager.LocationManager;
import com.lanthanh.admin.icareapp.data.manager.TypeManager;
import com.lanthanh.admin.icareapp.data.manager.VoucherManager;
import com.lanthanh.admin.icareapp.domain.model.Country;
import com.lanthanh.admin.icareapp.domain.model.DTOCity;
import com.lanthanh.admin.icareapp.domain.model.DTOCountry;
import com.lanthanh.admin.icareapp.domain.model.DTODistrict;
import com.lanthanh.admin.icareapp.domain.model.DTOLocation;
import com.lanthanh.admin.icareapp.domain.model.DTOType;
import com.lanthanh.admin.icareapp.domain.model.DTOVoucher;
import com.lanthanh.admin.icareapp.presentation.presenter.base.Presenter;
import com.lanthanh.admin.icareapp.presentation.view.BaseView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ADMIN on 03-Jan-17.
 */

public interface BookingSelectPresenter extends Presenter {
    interface View extends BaseView {
        void updateCountryList(List<String> list);
        void updateCityList(List<String> list);
        void updateDistrictList(List<String> list);
        void updateLocationList(List<String> list);
        void updateVoucherList(List<String> list);
        void updateTypeList(List<String> list);
        void displayStartDate(String startDate);
        void displayExpireDate(String expireDate);
        void showStartDatePicker(Calendar calendar);
        void showExpireDatePicker(Calendar calendar);
        void onVoucherChange();
        void onTypeChange();
    }

    void getAllCountries();
    void getAllCitiesByCountry(String country);
    void getAllDistrictsByCity(String city);
    void getAllLocationsByDistrict(String district);
    void getAllVouchers();
    void getAllTypes();
    DTOCountry getCountry(String country);
    DTOCity getCity(String city);
    DTODistrict getDistrict(String city);
    DTOLocation getLocation(String location);
    DTOVoucher getVoucher(String voucher);
    DTOType getType(String type);
    void onCountrySelect(String country);
    void onCitySelect(String city);
    void onDistrictSelect(String district);
    void onLocationSelect(String location);
    void onVoucherSelect(String voucher);
    void onTypeSelect(String type);
    void onStartDateSet(Calendar startDate);
    void onExpireDateSet(Calendar expireDate);
    void onStartDatePickerClick();
    void onExpireDatePickerClick();
    void resetStartDate();
    void resetExpireDate();
    boolean isAllInfoFiiled();
}
