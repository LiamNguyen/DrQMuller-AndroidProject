package com.lanthanh.admin.icareapp.data.restapi;

import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;

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
    Observable<RepositorySimpleStatus> getCountries();
    Observable<RepositorySimpleStatus> getCitiesByCountryId(int countryId);
    Observable<RepositorySimpleStatus> getDistrictsByCityId(int cityId);
    Observable<RepositorySimpleStatus> getLocationsByDistrictId(int districtId);
    Observable<RepositorySimpleStatus> getVouchers();
    Observable<RepositorySimpleStatus> getTypes();
    Observable<RepositorySimpleStatus> getAllTime();
    Observable<RepositorySimpleStatus> getEcoTime();
    Observable<RepositorySimpleStatus> getSelectedTime(int dayId, int locationId, int machineId);
    Observable<RepositorySimpleStatus> getDaysOfWeek();
    Observable<RepositorySimpleStatus> getMachinesByLocationId(int locationId);
    Observable<RepositorySimpleStatus> bookTime(String authToken, RequestBody body);
    Observable<RepositorySimpleStatus> releaseTime(String authToken, RequestBody body);
    Observable<RepositorySimpleStatus> validateAppointment();
    Observable<RepositorySimpleStatus> createAppointment(String authToken, RequestBody body);
    Observable<RepositorySimpleStatus> confirmAppointment(String authToken, RequestBody body);
    Observable<RepositorySimpleStatus> cancelAppointment(String authToken, RequestBody body);
}
