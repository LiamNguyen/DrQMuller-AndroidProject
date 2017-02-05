package com.lanthanh.admin.icareapp.presentation.presenter.impl;

import android.util.Log;

import com.lanthanh.admin.icareapp.data.manager.CityManager;
import com.lanthanh.admin.icareapp.data.manager.CountryManager;
import com.lanthanh.admin.icareapp.data.manager.DistrictManager;
import com.lanthanh.admin.icareapp.data.manager.LocationManager;
import com.lanthanh.admin.icareapp.data.manager.TypeManager;
import com.lanthanh.admin.icareapp.data.manager.VoucherManager;
import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointment;
import com.lanthanh.admin.icareapp.threading.MainThread;
import com.lanthanh.admin.icareapp.domain.interactor.GetAllCitiesByCountryInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.GetAllCountriesInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.GetAllDistrictsByCityInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.GetAllLocationsByDistrictInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.GetAllTypesInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.GetAllVouchersInteractor;
import com.lanthanh.admin.icareapp.domain.interactor.impl.GetAllCitiesByCountryInteractorImpl;
import com.lanthanh.admin.icareapp.domain.interactor.impl.GetAllCountriesInteractorImpl;
import com.lanthanh.admin.icareapp.domain.interactor.impl.GetAllDistrictsByCityInteractorImpl;
import com.lanthanh.admin.icareapp.domain.interactor.impl.GetAllLocationsByDistrictInteractorImpl;
import com.lanthanh.admin.icareapp.domain.interactor.impl.GetAllTypesInteractorImpl;
import com.lanthanh.admin.icareapp.domain.interactor.impl.GetAllVouchersInteractorImpl;
import com.lanthanh.admin.icareapp.domain.model.DTOCity;
import com.lanthanh.admin.icareapp.domain.model.DTOCountry;
import com.lanthanh.admin.icareapp.domain.model.DTODistrict;
import com.lanthanh.admin.icareapp.domain.model.DTOLocation;
import com.lanthanh.admin.icareapp.domain.model.DTOType;
import com.lanthanh.admin.icareapp.domain.model.DTOVoucher;
import com.lanthanh.admin.icareapp.presentation.converter.ConverterForDisplay;
import com.lanthanh.admin.icareapp.presentation.presenter.BookingSelectPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.base.AbstractPresenter;

import java.util.Calendar;
import java.util.List;

/**
 * Created by ADMIN on 04-Jan-17.
 */

public class BookingSelectPresenterImpl extends AbstractPresenter implements BookingSelectPresenter,
        GetAllCountriesInteractor.Callback, GetAllCitiesByCountryInteractor.Callback, GetAllDistrictsByCityInteractor.Callback,
        GetAllLocationsByDistrictInteractor.Callback, GetAllVouchersInteractor.Callback, GetAllTypesInteractor.Callback{
    public static final String TAG = BookingSelectPresenterImpl.class.getSimpleName();
    private BookingSelectPresenter.View mView;
    private DTOAppointment dtoAppointment;
    private List<DTOCountry> countryList;
    private List<DTOCity> cityList;
    private List<DTODistrict> districtList;
    private List<DTOLocation> locationList;
    private List<DTOVoucher> voucherList;
    private List<DTOType> typeList;
    private CountryManager countryManager;
    private CityManager cityManager;
    private DistrictManager districtManager;
    private LocationManager locationManager;
    private VoucherManager voucherManager;
    private TypeManager typeManager;

    public BookingSelectPresenterImpl(Executor executor, MainThread mainThread, View view, DTOAppointment dtoAppointment,
    CountryManager countryManager, CityManager cityManager, DistrictManager districtManager, LocationManager locationManager, VoucherManager voucherManager, TypeManager typeManager){
        super(executor, mainThread);
        mView = view;
        this.dtoAppointment = dtoAppointment;
        this.countryManager = countryManager;
        this.cityManager = cityManager;
        this.districtManager = districtManager;
        this.locationManager = locationManager;
        this.voucherManager = voucherManager;
        this.typeManager = typeManager;
    }

    @Override
    public void resume() {

    }
    /*========================== COUNTRY ==========================*/
    @Override
    public void getAllCountries() {
        mView.showProgress();
        GetAllCountriesInteractor getAllCountriesInteractor = new GetAllCountriesInteractorImpl(mExecutor, mMainThread, this, countryManager);
        getAllCountriesInteractor.execute();
    }

    @Override
    public DTOCountry getCountry(String country) {
        for (DTOCountry dtoCountry: countryList){
            if (dtoCountry.getCountryName().equals(country))
                return dtoCountry;
        }
        return null;
    }

    //Receive country list from interactor
    @Override
    public void onAllCountriesReceive(List<DTOCountry> countryList) {
        try {
            this.countryList = countryList;
            List<String> list = ConverterForDisplay.convertToStringList(countryList);
            mView.updateCountryList(list);
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void onNoCountryFound() {
        try {
            Log.e(TAG, "No country found");
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    //Add country id when a country is selected from View
    @Override
    public void onCountrySelect(String country) {
        DTOCountry dtoCountry = getCountry(country);
        if (dtoCountry == null){
            onError("No id found for this country");
            return;
        }
        dtoAppointment.setCountry(dtoCountry);
    }

    /*========================== CITY ==========================*/
    @Override
    public void getAllCitiesByCountry(String country) {
        int id = getCountry(country).getId();
        if (id <= 0){
            onError("No id found for this country");
            return;
        }
        GetAllCitiesByCountryInteractor getAllCitiesByCountryInteractor = new GetAllCitiesByCountryInteractorImpl(mExecutor, mMainThread, this, cityManager, id);
        getAllCitiesByCountryInteractor.execute();
    }

    @Override
    public DTOCity getCity(String city) {
        for (DTOCity dtoCity: cityList){
            if (dtoCity.getCityName().equals(city))
                return dtoCity;
        }
        return null;
    }

    @Override
    public void onAllCitiesReceive(List<DTOCity> cityList) {
        try {
            this.cityList = cityList;
            List<String> list = ConverterForDisplay.convertToStringList(cityList);
            mView.updateCityList(list);
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void onNoCityFound() {
        try {
            Log.e(TAG, "No city found");
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    //Add city id when a city is selected from view
    @Override
    public void onCitySelect(String city) {
        DTOCity dtoCity = getCity(city);
        if (dtoCity == null){
            onError("No id found for this city");
            return;
        }
        dtoAppointment.setCity(dtoCity);
    }

    /*========================== DISTRICT ==========================*/
    @Override
    public void getAllDistrictsByCity(String city) {
        int id = getCity(city).getId();
        if (id <= 0){
            onError("No id found for this city");
            return;
        }
        GetAllDistrictsByCityInteractor getAllDistrictsByCityInteractor = new GetAllDistrictsByCityInteractorImpl(mExecutor, mMainThread, this, districtManager, id);
        getAllDistrictsByCityInteractor.execute();
    }

    @Override
    public DTODistrict getDistrict(String district) {
        for (DTODistrict dtoDistrict: districtList){
            if (dtoDistrict.getDistrictName().equals(district))
                return dtoDistrict;
        }
        return null;
    }

    @Override
    public void onAllDistrictsReceive(List<DTODistrict> districtList) {
        try {
            this.districtList = districtList;
            List<String> list = ConverterForDisplay.convertToStringList(districtList);
            mView.updateDistrictList(list);
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void onNoDistrictFound() {
        try {
            Log.e(TAG, "No district found");
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    //Add district id when a district is selected from View
    @Override
    public void onDistrictSelect(String district) {
        DTODistrict dtoDistrict = getDistrict(district);
        if (dtoDistrict == null){
            onError("No id found for this district");
            return;
        }
        dtoAppointment.setDistrict(dtoDistrict);
    }

    /*========================== LOCATION ==========================*/
    @Override
    public void getAllLocationsByDistrict(String district) {
        int id = getDistrict(district).getId();
        if (id <= 0){
            onError("No id found for this district");
            return;
        }
        GetAllLocationsByDistrictInteractor getAllLocationsByDistrictInteractor = new GetAllLocationsByDistrictInteractorImpl(mExecutor, mMainThread, this, locationManager, id);
        getAllLocationsByDistrictInteractor.execute();
    }

    @Override
    public DTOLocation getLocation(String location) {
        for (DTOLocation dtoLocation: locationList){
            if (dtoLocation.getLocationName().equals(location))
                return dtoLocation;
        }
        return null;
    }

    @Override
    public void onAllLocationsReceive(List<DTOLocation> locationList) {
        try {
            mView.hideProgress();
            this.locationList = locationList;
            List<String> list = ConverterForDisplay.convertToStringList(locationList);
            mView.updateLocationList(list);
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void onNoLocationFound() {
        try {
            mView.hideProgress();
            Log.e(TAG, "No location found");
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    //Add location id when a location is selected from View
    @Override
    public void onLocationSelect(String location) {
        DTOLocation dtoLocation = getLocation(location);
        if (dtoLocation == null){
            onError("No id found for this location");
            return;
        }
        dtoAppointment.setLocation(dtoLocation);
    }

    /*========================== VOUCHER ==========================*/
    @Override
    public void getAllVouchers() {
        GetAllVouchersInteractor getAllVouchersInteractor = new GetAllVouchersInteractorImpl(mExecutor, mMainThread, this, voucherManager);
        getAllVouchersInteractor.execute();
    }

    @Override
    public DTOVoucher getVoucher(String voucher) {
        for (DTOVoucher dtoVoucher: voucherList){
            if (dtoVoucher.toString().equals(voucher))
                return dtoVoucher;
        }
        return null;
    }

    @Override
    public void onAllVouchersReceive(List<DTOVoucher> voucherList) {
        try {
            this.voucherList = voucherList;
            List<String> list = ConverterForDisplay.convertToStringList(voucherList);
            mView.updateVoucherList(list);
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void onNoVoucherFound() {
        try {
            Log.e(TAG, "No voucher found");
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    //Add voucher when a voucher is selected from View
    @Override
    public void onVoucherSelect(String voucher) {
        DTOVoucher dtoVoucher = getVoucher(voucher);
        if (dtoVoucher == null) {
            onError("No id found for this voucher");
            return;
        }
        dtoAppointment.setVoucher(dtoVoucher);
        mView.onVoucherChange();
    }

    /*========================== TYPE ==========================*/
    @Override
    public void getAllTypes() {
        GetAllTypesInteractor getAllTypesInteractor = new GetAllTypesInteractorImpl(mExecutor, mMainThread, this, typeManager);
        getAllTypesInteractor.execute();
    }

    @Override
    public DTOType getType(String type) {
        for (DTOType dtoType: typeList){
            if (dtoType.getTypeName().equals(type))
                return dtoType;
        }
        return null;
    }

    @Override
    public void onAllTypesReceive(List<DTOType> typeList) {
        try {
            this.typeList = typeList;
            List<String> list = ConverterForDisplay.convertToStringList(typeList);
            mView.updateTypeList(list);
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    @Override
    public void onNoTypeFound() {
        try {
            Log.e(TAG, "No type found");
        }catch (Exception e){
            Log.w(TAG, e.toString());
        }
    }

    //Add type when a type is selected from View
    @Override
    public void onTypeSelect(String type) {
        DTOType dtoType = getType(type);
        if (dtoType == null) {
            onError("No id found for this type");
            return;
        }
        dtoAppointment.setType(dtoType);
        mView.onTypeChange();
    }

    /*==================================================================*/
    @Override
    public boolean isAllInfoFilled() {
        return dtoAppointment.isFirstSelectFilled();
    }
}
