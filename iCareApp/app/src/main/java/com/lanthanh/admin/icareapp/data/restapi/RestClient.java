package com.lanthanh.admin.icareapp.data.restapi;

import com.lanthanh.admin.icareapp.data.model.BookedAppointment;
import com.lanthanh.admin.icareapp.data.model.BookedSchedule;
import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.presentation.Function;
import com.lanthanh.admin.icareapp.presentation.model.UserInfo;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOCity;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOCountry;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTODistrict;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOLocation;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOMachine;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOTime;
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
    RequestBody createRequestBody(String json);
    Observable<RepositorySimpleStatus> login(Function.Void<UserInfo> saveUser, String username, String password);
    Observable<RepositorySimpleStatus> signup(Function.Void<UserInfo> saveUser, String username, String password);
    Observable<RepositorySimpleStatus> updateBasicInfo(Function.Void<UserInfo> saveUser, String authToken, String userId, String name, String address);
    Observable<RepositorySimpleStatus> updateNecessaryInfo(Function.Void<UserInfo> saveUser, String authToken, String userId, String dob, String gender);
    Observable<RepositorySimpleStatus> updateImportantInfo(Function.Void<UserInfo> saveUser, String authToken, String userId, String email, String phone);
    Observable<RepositorySimpleStatus> updateCustomerInfo(Function.Void<UserInfo> saveUser, String authToken, String userId, String name, String address, String dob, String gender, String email, String phone);
    Observable<List<DTOCountry>> getCountries();
    Observable<List<DTOCity>> getCitiesByCountryId(int countryId);
    Observable<List<DTODistrict>> getDistrictsByCityId(int cityId);
    Observable<List<DTOLocation>> getLocationsByDistrictId(int districtId);
    Observable<List<DTOVoucher>> getVouchers();
    Observable<List<DTOType>> getTypes();
    Observable<List<DTOTime>> getAllTime();
    Observable<List<DTOTime>> getEcoTime();
    Observable<List<DTOTime>> getSelectedTime(int dayId, int locationId, int machineId);
    Observable<List<DTOWeekDay>> getDaysOfWeek();
    Observable<List<DTOMachine>> getMachinesByLocationId(int locationId);
    Observable<RepositorySimpleStatus> bookTime(String authToken, BookedSchedule bookedSchedule);
    Observable<RepositorySimpleStatus> releaseTime(String authToken, BookedSchedule bookedSchedules);
    Observable<RepositorySimpleStatus> validateAppointment();
    Observable<String> createAppointment(String authToken, BookedAppointment appointment);
    Observable<RepositorySimpleStatus> confirmAppointment(String authToken, String userId, String appointmentId);
    Observable<RepositorySimpleStatus> cancelAppointment(String authToken, String userId, String appointmentId);
}
