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
import com.lanthanh.admin.icareapp.presentation.application.ApplicationProvider;
import com.lanthanh.admin.icareapp.presentation.base.BaseFragment;
import com.lanthanh.admin.icareapp.presentation.base.Presenter;
import com.lanthanh.admin.icareapp.presentation.model.DTOAppointment;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOCity;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOCountry;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTODistrict;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOLocation;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOType;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOVoucher;
import com.lanthanh.admin.icareapp.presentation.presenter.BookingActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.BookingSelectPresenter;
import com.lanthanh.admin.icareapp.presentation.adapter.CustomSpinnerAdapter;
import com.lanthanh.admin.icareapp.threading.impl.MainThreadImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ADMIN on 13-Nov-16.
 */

public class BookingSelectFragment extends BaseFragment<BookingActivityPresenterImpl> implements AdapterView.OnItemSelectedListener{
    @BindView(R.id.drop_down_icon_locations) AppCompatImageView locationIv;
    @BindView(R.id.drop_down_icon_vouchers) AppCompatImageView voucherIv;
    @BindView(R.id.drop_down_icon_types) AppCompatImageView typeIv;
    @BindView(R.id.spinner_countries) Spinner countrySp;
    @BindView(R.id.spinner_cities) Spinner citySp;
    @BindView(R.id.spinner_districts) Spinner districtSp;
    @BindView(R.id.spinner_locations) Spinner locationSp;
    @BindView(R.id.spinner_vouchers) Spinner voucherSp;
    @BindView(R.id.spinner_type) Spinner typeSp;
    @BindView(R.id.fab) FloatingActionButton nextButton;
    @BindView(R.id.type_container) RelativeLayout typeContainer;

    private CustomSpinnerAdapter countryAdapter, cityAdapter, districtAdapter, locationAdapter, voucherAdapter, typeAdapter;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_select, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();

        getMainPresenter().getCountries();
        getMainPresenter().getVouchers();
        getMainPresenter().getTypes();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void initViews() {
        /* =============================== DROP DOWN IMAGE VIEW =============================== */
        setImageTint(locationIv, false);
        setImageTint(voucherIv, false);
        setImageTint(typeIv, false);
        locationIv.setEnabled(false);
        voucherIv.setEnabled(false);
        typeIv.setEnabled(false);

        /* =============================== SPINNER =============================== */
        //Set up Spinners
        countrySp.setOnTouchListener(
                (View v, MotionEvent event) -> {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        showToast(getString(R.string.temp_inform));
                        return true;
                    }
                    return false;
                }
        );
        citySp.setOnTouchListener(
                (View v, MotionEvent event) -> {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        showToast(getString(R.string.temp_inform));
                        return true;
                    }
                    return false;
                });
        districtSp.setOnTouchListener(
                (View v, MotionEvent event) -> {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        showToast(getString(R.string.temp_inform));
                        return true;
                    }
                    return false;
                }
        );
        locationSp.setEnabled(false);
        voucherSp.setEnabled(false);
        typeContainer.setVisibility(View.INVISIBLE);

        //Set up adapter for spinner
        countryAdapter = new CustomSpinnerAdapter<>(getActivity(), R.layout.bookingselect_spinner_item, getProvider().getCountries(), getString(R.string.booking_country_hint));
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySp.setAdapter(countryAdapter);
        countrySp.setSelection(0, false);

        cityAdapter = new CustomSpinnerAdapter<>(getActivity(), R.layout.bookingselect_spinner_item, getProvider().getCities(), getString(R.string.booking_city_hint));
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySp.setAdapter(cityAdapter);
        citySp.setSelection(0, false);

        districtAdapter = new CustomSpinnerAdapter<>(getActivity(), R.layout.bookingselect_spinner_item, getProvider().getDistricts(), getString(R.string.booking_district_hint));
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        districtSp.setAdapter(districtAdapter);
        districtSp.setSelection(0, false);

        locationAdapter = new CustomSpinnerAdapter<>(getActivity(), R.layout.bookingselect_spinner_item, getProvider().getLocations(), getString(R.string.booking_location_hint));
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSp.setAdapter(locationAdapter);
        locationSp.setSelection(0, false);

        voucherAdapter = new CustomSpinnerAdapter<>(getActivity(), R.layout.bookingselect_spinner_item, getProvider().getVouchers(), getString(R.string.booking_voucher_hint));
        voucherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        voucherSp.setAdapter(voucherAdapter);
        voucherSp.setSelection(0, false);

        typeAdapter = new CustomSpinnerAdapter<>(getActivity(), R.layout.bookingselect_spinner_item, getProvider().getTypes(), getString(R.string.booking_type_hint));
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSp.setAdapter(typeAdapter);
        typeSp.setSelection(0, false);

        //Set Listeners for Spinners
        countrySp.setOnItemSelectedListener(this);
        citySp.setOnItemSelectedListener(this);
        districtSp.setOnItemSelectedListener(this);
        locationSp.setOnItemSelectedListener(this);
        voucherSp.setOnItemSelectedListener(this);
        typeSp.setOnItemSelectedListener(this);

        nextButton.setOnClickListener(view -> getMainPresenter().navigateFragment(BookingSelectDateFragment.class));
        nextButton.setEnabled(false);
    }

    @Override
    public void resetViews() {

    }

    @Override
    public BookingActivityPresenterImpl getMainPresenter() {
        return ((BookingActivity) getActivity()).getMainPresenter();
    }

    @Override
    public ApplicationProvider getProvider() {
        return ((BookingActivity) getActivity()).getProvider();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
//        if (!hidden && isVisible())
//            aController.setRequestData(getActivity(), this, ModelURL.SELECT_COUNTRIES.getUrl(MainActivity.isUAT), "");
    }

    public void setDefaultSelectionForCountry() {
        countrySp.setSelection(235);
    }

    public void setDefaultSelectionForCity() {
        citySp.setSelection(58);
    }

    public void setDefaultSelectionForDistrict() {
        districtSp.setSelection(8);
    }

    public void setDefaultSelectionForType() {
        typeSp.setSelection(2);
    }

    public void enableLocationSelection() {
        locationSp.setEnabled(true);
        locationIv.setEnabled(true);
        setImageTint(locationIv, true);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.spinner_countries:
                getMainPresenter().getCitiesByCountryId((DTOCountry) countrySp.getSelectedItem());
                break;
            case R.id.spinner_cities:
                getMainPresenter().getDistrictsByCityId((DTOCity) countrySp.getSelectedItem());
                break;
            case R.id.spinner_districts:
                getMainPresenter().getLocationsByDistrictId((DTODistrict) countrySp.getSelectedItem());
                break;
            case R.id.spinner_locations:
                getProvider().getCurrentAppointment().setLocation((DTOLocation) locationSp.getSelectedItem());
                voucherSp.setEnabled(true);
                voucherIv.setEnabled(true);
                setImageTint(voucherIv, true);
                break;
            case R.id.spinner_vouchers:
                getProvider().getCurrentAppointment().setVoucher((DTOVoucher) voucherSp.getSelectedItem());
                typeSp.setEnabled(true);
                typeIv.setEnabled(true);
                setImageTint(typeIv, true);
                break;
            case R.id.spinner_type:
                getProvider().getCurrentAppointment().setType((DTOType) typeSp.getSelectedItem());
            default:
                break;
        }
        if (getProvider().getCurrentAppointment().isFirstSelectFilled()) {
            nextButton.setEnabled(true);
        } else {
            nextButton.setEnabled(false);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}


    //TODO check this one and remove it
//    @Override
//    public void onVoucherChange() {
//        bookingActivityPresenter.emptyCart();
//    }
//
//    @Override
//    public void onTypeChange() {
//        bookingActivityPresenter.emptyCart();
//    }
//
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
