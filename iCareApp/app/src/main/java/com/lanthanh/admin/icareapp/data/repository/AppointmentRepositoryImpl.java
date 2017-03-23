package com.lanthanh.admin.icareapp.data.repository;

import com.lanthanh.admin.icareapp.data.restapi.RestClient;
import com.lanthanh.admin.icareapp.data.restapi.impl.RestClientImpl;
import com.lanthanh.admin.icareapp.domain.repository.AppointmentRepository;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOCity;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOCountry;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTODistrict;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOLocation;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOMachine;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOTime;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOTimeEco;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOTimeSelected;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOType;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOVoucher;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOWeekDay;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by long.vu on 3/23/2017.
 */

public class AppointmentRepositoryImpl implements AppointmentRepository {
    private RestClient restClient;

    public AppointmentRepositoryImpl(){
        restClient = RestClientImpl.createRestClient();
    }

    @Override
    public Observable<List<DTOCountry>> getCountries() {
        return restClient.getCountries();
    }

    @Override
    public Observable<List<DTOCity>> getCitiesByCountryId(int countryId) {
        return restClient.getCitiesByCountryId(countryId);
    }

    @Override
    public Observable<List<DTODistrict>> getDistrictsByCityId(int cityId) {
        return restClient.getDistrictsByCityId(cityId);
    }

    @Override
    public Observable<List<DTOLocation>> getLocationsByDistrictId(int districtId) {
        return restClient.getLocationsByDistrictId(districtId);
    }

    @Override
    public Observable<List<DTOVoucher>> getVouchers() {
        return restClient.getVouchers();
    }

    @Override
    public Observable<List<DTOType>> getTypes() {
        return restClient.getTypes();
    }

    @Override
    public Observable<List<DTOMachine>> getMachinesByLocationId(int locationId) {
        return restClient.getMachinesByLocationId(locationId);
    }

    @Override
    public Observable<List<DTOTime>> getAllTime() {
        return restClient.getAllTime();
    }

    @Override
    public Observable<List<DTOTimeSelected>> getSelectedTime(int dayId, int locationId, int machineId) {
        return restClient.getSelectedTime(dayId, locationId, machineId);
    }

    @Override
    public Observable<List<DTOTimeEco>> getEcoTime() {
        return restClient.getEcoTime();
    }

    @Override
    public Observable<List<DTOWeekDay>> getWeekDays() {
        return restClient.getDaysOfWeek();
    }
}
