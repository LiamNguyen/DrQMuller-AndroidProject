package com.lanthanh.admin.icareapp.data.repository;

import android.content.Context;

import com.lanthanh.admin.icareapp.data.model.DataMapper;
import com.lanthanh.admin.icareapp.data.repository.datasource.LocalStorage;
import com.lanthanh.admin.icareapp.data.repository.datasource.sqlite.iCareDb;
import com.lanthanh.admin.icareapp.data.restapi.RestClient;
import com.lanthanh.admin.icareapp.data.restapi.impl.RestClientImpl;
import com.lanthanh.admin.icareapp.domain.repository.AppointmentRepository;
import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.exceptions.UseCaseException;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOAppointment;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOAppointmentSchedule;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by long.vu on 3/23/2017.
 */

public class AppointmentRepositoryImpl implements AppointmentRepository {
    private RestClient restClient;
    private LocalStorage localStorage;
    private DataMapper dataMapper;
    private iCareDb mDb;

    public AppointmentRepositoryImpl(Context context){
        restClient = RestClientImpl.createRestClient();
        localStorage =  new LocalStorage(context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE));
        dataMapper = new DataMapper();
        mDb = iCareDb.getDatabase(context);
    }

    @Override
    public Observable<List<DTOCountry>> getCountries() {
        List<DTOCountry> result = mDb.getCountries();
        if (result.isEmpty())
            return restClient.getCountries().map(
                    resp -> {
                        mDb.addCoutries(resp);
                        return resp;
                    }
            );
        else
            return Observable.just(result);
    }

    @Override
    public Observable<List<DTOCity>> getCitiesByCountryId(int countryId) {
        List<DTOCity> result = mDb.getCitiesByCountryId(countryId);
        if (result.isEmpty())
            return restClient.getCitiesByCountryId(countryId).map(
                    resp -> {
                        mDb.addCities(resp, countryId);
                        return resp;
                    }
            );
        else
            return Observable.just(result);
    }

    @Override
    public Observable<List<DTODistrict>> getDistrictsByCityId(int cityId) {
        List<DTODistrict> result = mDb.getDistrictsByCityId(cityId);
        if (result.isEmpty())
            return restClient.getDistrictsByCityId(cityId).map(
                    resp -> {
                        mDb.addDistricts(resp, cityId);
                        return resp;
                    }
            );
        else
            return Observable.just(result);
    }

    @Override
    public Observable<List<DTOLocation>> getLocationsByDistrictId(int districtId) {
        List<DTOLocation> result = mDb.getLocationsByDistrictId(districtId);
        if (result.isEmpty())
            return restClient.getLocationsByDistrictId(districtId).map(
                    resp -> {
                        mDb.addLocations(resp, districtId);
                        return resp;
                    }
            );
        else
            return Observable.just(result);
    }

    @Override
    public Observable<List<DTOVoucher>> getVouchers() {
        List<DTOVoucher> result = mDb.getVouchers();
        if (result.isEmpty())
            return restClient.getVouchers().map(
                    resp -> {
                        mDb.addVouchers(resp);
                        return resp;
                    }
            );
        else
            return Observable.just(result);
    }

    @Override
    public Observable<List<DTOType>> getTypes() {
        List<DTOType> result = mDb.getTypes();
        if (result.isEmpty())
            return restClient.getTypes().map(
                    resp -> {
                        mDb.addTypes(resp);
                        return resp;
                    }
            );
        else
            return Observable.just(result);
    }

    @Override
    public Observable<List<DTOMachine>> getMachinesByLocationId(int locationId) {
        List<DTOMachine> result = mDb.getMachinesByLocationId(locationId);
        if (result.isEmpty())
            return restClient.getMachinesByLocationId(locationId).map(
                    resp -> {
                        mDb.addMachines(resp, locationId);
                        return resp;
                    }
            );
        else
            return Observable.just(result);
    }

    @Override
    public Observable<List<DTOTime>> getTime(int voucherId) {
        List<DTOTime> result;
        if (voucherId == 1) {
            result = mDb.getEcoTime();
            if (result.isEmpty())
                return restClient.getEcoTime().map(
                            resp -> {
                                mDb.addEcoTime(resp);
                                return resp;
                            }
                        );
            else
                return Observable.just(result);
        }
        else {
            result = mDb.getAllTime();
            if (result.isEmpty())
                return restClient.getAllTime().map(
                            resp -> {
                                mDb.addTime(resp);
                                return resp;
                            }
                        );
            else
                return Observable.just(result);
        }
    }

    @Override
    public Observable<List<DTOTime>> getAvailableTime(int dayId, int locationId, int machineId, int voucherId) {
        return restClient.getSelectedTime(dayId, locationId, machineId).map(
                resp -> {
                    List<DTOTime> available = new ArrayList<>();
                    if (voucherId == 1) {
                        available.addAll(mDb.getEcoTime());
                    } else {
                        available.addAll(mDb.getAllTime());
                    }
                    for (DTOTime time : resp) {
                        for (DTOTime _time : new ArrayList<>(available)) {
                            if (_time.getTimeId() == time.getTimeId())
                                available.remove(_time);
                        }
                    }
                    return available;
                }
        );
    }

    @Override
    public Observable<List<DTOWeekDay>> getWeekDays(int voucherId) {
        List<DTOWeekDay> result = mDb.getWeekDays();
        if (result.isEmpty()) {
            return restClient.getDaysOfWeek().map(
                    resp -> {
                        mDb.addWeekDays(resp);
                        if (voucherId == 1) {
                            for (DTOWeekDay day : new ArrayList<>(resp)) {
                                if (day.getDayId() == 6 || day.getDayId() == 7)
                                    resp.remove(day);
                            }
                        }
                        return resp;
                    }
            );
        } else {
            if (voucherId == 1) {
                for (DTOWeekDay day : new ArrayList<>(result)) {
                    if (day.getDayId() == 6 || day.getDayId() == 7)
                        result.remove(day);
                }
            }
            return Observable.just(result);
        }
    }

    @Override
    public Observable<RepositorySimpleStatus> bookTime(int locationId, DTOAppointmentSchedule appointmentSchedule) {
        UserInfo user = localStorage.getUserFromLocal();
        return restClient.bookTime(user.getToken(), dataMapper.transform(locationId, appointmentSchedule)).concatMap(
                resp -> {
                    if (resp == RepositorySimpleStatus.SUCCESS)
                        return Observable.just(resp);
                    return Observable.error(new UseCaseException(resp));
                }
        );
    }

    @Override
    public Observable<RepositorySimpleStatus> releaseTime(int locationId, List<DTOAppointmentSchedule> appointmentScheduleList) {
        UserInfo user = localStorage.getUserFromLocal();
        return restClient.releaseTime(user.getToken(), dataMapper.transform(locationId, appointmentScheduleList)).concatMap(
                resp -> {
                    if (resp == RepositorySimpleStatus.SUCCESS)
                        return Observable.just(resp);
                    return Observable.error(new UseCaseException(resp));
                }
        );
    }

    @Override
    public Observable<RepositorySimpleStatus> validateAppointment() {
        return restClient.validateAppointment().concatMap(
                resp -> {
                    if (resp == RepositorySimpleStatus.SUCCESS)
                        return Observable.just(resp);
                    return Observable.error(new UseCaseException(resp));
                }
        );
    }

    @Override
    public Observable<RepositorySimpleStatus> createAppointment(DTOAppointment appointment) {
        UserInfo user = localStorage.getUserFromLocal();
        appointment.setUser(user);
        return restClient.createAppointment(user.getToken(), dataMapper.transform(appointment)).concatMap(
                resp -> {
                    if (!resp.isEmpty()) {
                        List<DTOAppointment> appointmentList = localStorage.getAppointmentsFromLocal();
                        appointment.setAppointmentId(resp);
                        appointmentList.add(appointment);
                        localStorage.saveAppointmentsToLocal(appointmentList);
                        return Observable.just(RepositorySimpleStatus.SUCCESS);
                    }
                    return Observable.error(new UseCaseException(RepositorySimpleStatus.UNKNOWN_ERROR));
                }
        );
    }

    @Override
    public Observable<RepositorySimpleStatus> confirmAppointment(String appointmentId) {
        UserInfo user = localStorage.getUserFromLocal();
        return restClient.confirmAppointment(user.getToken(), user.getId(), appointmentId).concatMap(
                resp -> {
                    if (resp == RepositorySimpleStatus.SUCCESS) {
                        List<DTOAppointment> appointmentList = localStorage.getAppointmentsFromLocal();
                        for (DTOAppointment appointment: appointmentList) {
                            if (appointment.getAppointmentId().equals(appointmentId)) {
                                appointment.setStatus(true);
                                break;
                            }
                        }
                        localStorage.saveAppointmentsToLocal(appointmentList);
                        return Observable.just(RepositorySimpleStatus.SUCCESS);
                    }
                    return Observable.error(new UseCaseException(resp));
                }
        );
    }

    @Override
    public Observable<RepositorySimpleStatus> cancelAppointment(String appointmentId) {
        UserInfo user = localStorage.getUserFromLocal();
        return restClient.cancelAppointment(user.getToken(), user.getId(), appointmentId).concatMap(
                resp -> {
                    if (resp == RepositorySimpleStatus.SUCCESS) {
                        List<DTOAppointment> appointmentList = localStorage.getAppointmentsFromLocal();
                        for (DTOAppointment appointment : new ArrayList<>(appointmentList)) {
                            if (appointment.getAppointmentId().equals(appointmentId))
                                appointmentList.remove(appointment);
                        }
                        localStorage.saveAppointmentsToLocal(appointmentList);
                        return Observable.just(RepositorySimpleStatus.SUCCESS);
                    }
                    return Observable.error(new UseCaseException(resp));
                }
        );
    }

    @Override
    public Observable<List<DTOAppointment>> getAppointments() {
        return Observable.just(localStorage.getAppointmentsFromLocal()).map(
                appointments -> {
                    for (DTOAppointment app: new ArrayList<>(appointments)) {
                        Calendar expireDate = Calendar.getInstance();
                        expireDate.setTime(app.getExpireDate());
                        Calendar calendarNow = Calendar.getInstance();
                        if (expireDate.get(Calendar.DATE) < calendarNow.get(Calendar.DATE)) {
                            appointments.remove(app);
                        }
                    }
                    localStorage.saveAppointmentsToLocal(appointments);
                    return appointments;
                }
        );
    }

    @Override
    public Observable<RepositorySimpleStatus> sendEmailNotifyBooking(String appointmentId) {
        return restClient.sendEmailNotifyBooking(appointmentId).concatMap(
            resp -> {
                if (resp == RepositorySimpleStatus.SUCCESS) {
                    List<DTOAppointment> appointmentList = localStorage.getAppointmentsFromLocal();
                    for (DTOAppointment appointment: appointmentList) {
                        if (appointment.getAppointmentId().equals(appointmentId)) {
                            appointment.setEmailSent(true);
                            break;
                        }
                    }
                    localStorage.saveAppointmentsToLocal(appointmentList);
                    return Observable.just(RepositorySimpleStatus.SUCCESS);
                }
                return Observable.error(new UseCaseException(resp));
            }
        );
    }


}
