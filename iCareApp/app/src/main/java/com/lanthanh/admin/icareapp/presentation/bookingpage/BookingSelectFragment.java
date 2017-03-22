package com.lanthanh.admin.icareapp.presentation.bookingpage;

import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.api.impl.iCareApiImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.CityManagerImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.CountryManagerImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.DistrictManagerImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.LocationManagerImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.TypeManagerImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.VoucherManagerImpl;
import com.lanthanh.admin.icareapp.domain.executor.impl.ThreadExecutor;
import com.lanthanh.admin.icareapp.presentation.presenter.BookingActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.BookingSelectPresenter;
import com.lanthanh.admin.icareapp.presentation.adapter.CustomSpinnerAdapter;
import com.lanthanh.admin.icareapp.threading.impl.MainThreadImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 13-Nov-16.
 */

public class BookingSelectFragment extends Fragment implements BookingSelectPresenter.View, AdapterView.OnItemSelectedListener, View.OnClickListener{
    private BookingSelectPresenter bookingSelectPresenter;
    private BookingActivityPresenter bookingActivityPresenter;
    private List<String> countriesName, citiesName, districtsName, locationsName, vouchersName, typesName;
    private CustomSpinnerAdapter countryAdapter, cityAdapter, districtAdapter, locationAdapter, voucherAdapter, typeAdapter;
    private Spinner countrySp, citySp, districtSp, locationSp, voucherSp, typeSp;
    private AppCompatImageView locationIv, voucherIv, typeIv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_select, container, false);

        init();

        /* =============================== DROP DOWN IMAGE VIEW =============================== */
        locationIv = (AppCompatImageView) view.findViewById(R.id.drop_down_icon_locations);
        voucherIv = (AppCompatImageView) view.findViewById(R.id.drop_down_icon_vouchers);
        typeIv = (AppCompatImageView) view.findViewById(R.id.drop_down_icon_types);
        setImageTint(locationIv, false);
        setImageTint(voucherIv, false);
        setImageTint(typeIv, false);
        locationIv.setEnabled(false);
        voucherIv.setEnabled(false);
        typeIv.setEnabled(false);

        /* =============================== SPINNER =============================== */
        //Set up Spinners
        countrySp = (Spinner) view.findViewById(R.id.spinner_countries);
        countrySp.setEnabled(true);
        countrySp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showError(getString(R.string.temp_inform));
                    return true;
                }
                return false;
            }
        });

        citySp = (Spinner) view.findViewById(R.id.spinner_cities);
        citySp.setEnabled(true);
        citySp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showError(getString(R.string.temp_inform));
                    return true;
                }
                return false;
            }
        });

        districtSp = (Spinner) view.findViewById(R.id.spinner_districts);
        districtSp.setEnabled(true);
        districtSp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showError(
                            getString(R.string.temp_inform));
                    return true;
                }
                return false;
            }
        });

        locationSp = (Spinner) view.findViewById(R.id.spinner_locations);
        locationSp.setEnabled(false);

        voucherSp = (Spinner) view.findViewById(R.id.spinner_vouchers);
        voucherSp.setEnabled(false);

        typeSp = (Spinner) view.findViewById(R.id.spinner_type);
        typeSp.setEnabled(true);
        RelativeLayout typeContainer = (RelativeLayout) view.findViewById(R.id.type_container);
        typeContainer.setVisibility(View.INVISIBLE);

//        machineSp = (Spinner) view.findViewById(R.id.spinner_machine);
//        machineSp.setEnabled(false);

        //Set up adapter for spinner
        countryAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.bookingselect_spinner_item, countriesName);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySp.setAdapter(countryAdapter);
        countrySp.setSelection(0, false);

        cityAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.bookingselect_spinner_item, citiesName);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySp.setAdapter(cityAdapter);
        citySp.setSelection(0, false);

        districtAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.bookingselect_spinner_item, districtsName);
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        districtSp.setAdapter(districtAdapter);
        districtSp.setSelection(0, false);

        locationAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.bookingselect_spinner_item, locationsName);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSp.setAdapter(locationAdapter);
        locationSp.setSelection(0, false);

        voucherAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.bookingselect_spinner_item, vouchersName);
        voucherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        voucherSp.setAdapter(voucherAdapter);
        voucherSp.setSelection(0, false);

        typeAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.bookingselect_spinner_item, typesName);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSp.setAdapter(typeAdapter);
        typeSp.setSelection(0, false);

//        machineAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.bookingselect_spinner_item, machinesName);
//        machineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        machineSp.setAdapter(machineAdapter);
//        machineSp.setSelection(0, false);

        //Set Listeners for Spinners
        countrySp.setOnItemSelectedListener(this);
        citySp.setOnItemSelectedListener(this);
        districtSp.setOnItemSelectedListener(this);
        locationSp.setOnItemSelectedListener(this);
        voucherSp.setOnItemSelectedListener(this);
        typeSp.setOnItemSelectedListener(this);
//        machineSp.setOnItemSelectedListener(this);

        /* =============================== LAST STEP =============================== */
        //Initialize data for Country Spinner first
        bookingSelectPresenter.getAllCountries();
        bookingSelectPresenter.getAllVouchers();
        bookingSelectPresenter.getAllTypes();

        FloatingActionButton nextBut = (FloatingActionButton) view.findViewById(R.id.fab);
        nextBut.setOnClickListener(this);

        return view;
    }

    public void init(){
        //Init presenter
        bookingActivityPresenter = ((BookingActivity) getActivity()).getMainPresenter();
        bookingSelectPresenter = new BookingSelectPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this, bookingActivityPresenter.getDTOAppointment(),
        new CountryManagerImpl(iCareApiImpl.getAPI()), new CityManagerImpl(iCareApiImpl.getAPI()),
        new DistrictManagerImpl(iCareApiImpl.getAPI()), new LocationManagerImpl(iCareApiImpl.getAPI()),
        new VoucherManagerImpl(iCareApiImpl.getAPI()), new TypeManagerImpl(iCareApiImpl.getAPI()));
        //Init data and the first string is hint
        countriesName = new ArrayList<>();
        countriesName.add(getString(R.string.booking_country_hint));
        citiesName = new ArrayList<>();
        citiesName.add(getString(R.string.booking_city_hint));
        districtsName = new ArrayList<>();
        districtsName.add(getString(R.string.booking_district_hint));
        locationsName = new ArrayList<>();
        locationsName.add(getString(R.string.booking_location_hint));
        vouchersName = new ArrayList<>();
        vouchersName.add(getString(R.string.booking_voucher_hint));
        typesName = new ArrayList<>();
        typesName.add(getString(R.string.booking_type_hint));
//        machinesName = new ArrayList<>();
//        machinesName.add(getString(R.string.booking_machine_hint));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
//        if (!hidden && isVisible())
//            aController.setRequestData(getActivity(), this, ModelURL.SELECT_COUNTRIES.getUrl(MainActivity.isUAT), "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab:
                if (bookingSelectPresenter.isAllInfoFilled())
                    bookingActivityPresenter.navigateTab(BookingActivity.SECOND_SELECT);
                else
                    showError(getString(R.string.missing_detail));
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.spinner_countries:
                bookingSelectPresenter.getAllCitiesByCountry(countrySp.getSelectedItem().toString());
                bookingSelectPresenter.onCountrySelect(countrySp.getSelectedItem().toString());
                break;
            case R.id.spinner_cities:
                bookingSelectPresenter.getAllDistrictsByCity(citySp.getSelectedItem().toString());
                bookingSelectPresenter.onCitySelect(citySp.getSelectedItem().toString());
                break;
            case R.id.spinner_districts:
                bookingSelectPresenter.getAllLocationsByDistrict(districtSp.getSelectedItem().toString());
                bookingSelectPresenter.onDistrictSelect(districtSp.getSelectedItem().toString());
                break;
            case R.id.spinner_locations:
                //bookingSelectPresenter.getAllVouchers();
                bookingSelectPresenter.onLocationSelect(locationSp.getSelectedItem().toString());
                voucherSp.setEnabled(true);
                voucherIv.setEnabled(true);
                setImageTint(voucherIv, true);
                break;
            case R.id.spinner_vouchers:
                //bookingSelectPresenter.getAllTypes();
                bookingSelectPresenter.onVoucherSelect(voucherSp.getSelectedItem().toString());
                typeSp.setEnabled(true);
                typeIv.setEnabled(true);
                setImageTint(typeIv, true);
                break;
            case R.id.spinner_type:
                bookingSelectPresenter.onTypeSelect(typeSp.getSelectedItem().toString());
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void updateCountryList(List<String> list) {
        countriesName.clear();
        countriesName.add(getString(R.string.booking_country_hint));
        countriesName.addAll(list);
        countrySp.setSelection(235);
    }

    @Override
    public void updateCityList(List<String> list) {
        citiesName.clear();
        citiesName.add(getString(R.string.booking_city_hint));
        citiesName.addAll(list);
        citySp.setSelection(58);
    }

    @Override
    public void updateDistrictList(List<String> list) {
        districtsName.clear();
        districtsName.add(getString(R.string.booking_district_hint));
        districtsName.addAll(list);
        districtSp.setSelection(8);
    }

    @Override
    public void updateLocationList(List<String> list) {
        locationsName.clear();
        locationsName.add(getString(R.string.booking_location_hint));
        locationsName.addAll(list);
        locationSp.setEnabled(true);
        locationIv.setEnabled(true);
        setImageTint(locationIv, true);
    }

    @Override
    public void updateVoucherList(List<String> list) {
        vouchersName.clear();
        vouchersName.add(getString(R.string.booking_voucher_hint));
        vouchersName.addAll(list);
    }

    @Override
    public void updateTypeList(List<String> list) {
        typesName.clear();
        typesName.add(getString(R.string.booking_type_hint));
        typesName.addAll(list);
        typeSp.setSelection(2);
    }

    @Override
    public void onVoucherChange() {
        bookingActivityPresenter.emptyCart();
    }

    @Override
    public void onTypeChange() {
        bookingActivityPresenter.emptyCart();
    }

    @Override
    public void showProgress() {
        ((BookingActivity) getActivity()).showProgress();
    }

    @Override
    public void hideProgress() {
        ((BookingActivity) getActivity()).hideProgress();
    }

    @Override
    public void showError(String message) {
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 110);
        toast.show();
    }

    @Override
    public void setImageTint(AppCompatImageView imageView, boolean isEnabled) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //for M and above (API >= 23)
            if (isEnabled)
                imageView.setColorFilter(getResources().getColor(R.color.colorWhite, null), PorterDuff.Mode.SRC_ATOP);
            else
                imageView.setColorFilter(getResources().getColor(R.color.colorPrimaryDark, null), PorterDuff.Mode.SRC_ATOP);
        } else{
            //below M (API <23)
            if (isEnabled)
                imageView.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
            else
                imageView.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        }
    }
}
