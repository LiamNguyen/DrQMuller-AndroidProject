package com.lanthanh.admin.icareapp.data.restapi;

import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
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
import okhttp3.RequestBody;

/**
 * Created by ADMIN on 19-Feb-17.
 */

public interface RestClient {
    RequestBody createRequestBody(String[] keys, String[] values);
    Observable<RepositorySimpleStatus> login(String username, String password);
    Observable<RepositorySimpleStatus> signup(String username, String password);
    Observable<RepositorySimpleStatus> updateBasicInfo(String authToken, String name, String address);
    Observable<RepositorySimpleStatus> updateNecessaryInfo(String authToken, String dob, String gender);
    Observable<RepositorySimpleStatus> updateImportantInfo(String authToken, String email, String phone);
    Observable<List<DTOCountry>> getCountries();
    Observable<List<DTOCity>> getCitiesByCountryId(int countryId);
    Observable<List<DTODistrict>> getDistrictsByCityId(int cityId);
    Observable<List<DTOLocation>> getLocationsByDistrictId(int districtId);
    Observable<List<DTOVoucher>> getVouchers();
    Observable<List<DTOType>> getTypes();
    Observable<List<DTOTime>> getAllTime();
    Observable<List<DTOTimeEco>> getEcoTime();
    Observable<List<DTOTimeSelected>> getSelectedTime(int dayId, int locationId, int machineId);
    Observable<List<DTOWeekDay>> getDaysOfWeek();
    Observable<List<DTOMachine>> getMachinesByLocationId(int locationId);
    Observable<RepositorySimpleStatus> bookTime(String authToken, RequestBody body);
    Observable<RepositorySimpleStatus> releaseTime(String authToken, RequestBody body);
    Observable<RepositorySimpleStatus> validateAppointment();
    Observable<RepositorySimpleStatus> createAppointment(String authToken, RequestBody body);
    Observable<RepositorySimpleStatus> confirmAppointment(String authToken, RequestBody body);
    Observable<RepositorySimpleStatus> cancelAppointment(String authToken, RequestBody body);
}
