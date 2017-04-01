package com.lanthanh.admin.icareapp.data.repository;

import android.content.Context;

import com.lanthanh.admin.icareapp.data.model.DataMapper;
import com.lanthanh.admin.icareapp.data.repository.datasource.LocalStorage;
import com.lanthanh.admin.icareapp.data.restapi.RestClient;
import com.lanthanh.admin.icareapp.data.restapi.impl.RestClientImpl;
import com.lanthanh.admin.icareapp.domain.repository.AppointmentRepository;
import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.presentation.application.ApplicationProvider;
import com.lanthanh.admin.icareapp.presentation.application.iCareApplication;
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
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by long.vu on 3/23/2017.
 */

public class AppointmentRepositoryImpl implements AppointmentRepository {
    private RestClient restClient;
    private ApplicationProvider provider;
    private LocalStorage localStorage;
    private DataMapper dataMapper;

    public AppointmentRepositoryImpl(Context context){
        restClient = RestClientImpl.createRestClient();
        localStorage =  new LocalStorage(context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE));
        provider = ((iCareApplication) context.getApplicationContext()).getProvider();
        dataMapper = new DataMapper();
    }

    @Override
    public Observable<List<DTOCountry>> getCountries() {
        if (provider.getCountries().isEmpty())
            return restClient.getCountries().map(
                    resp -> {
                        provider.setCountries(resp);
                        return resp;
                    }
            );
        else
            return Observable.just(provider.getCountries());
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
        if (provider.getVouchers().isEmpty())
            return restClient.getVouchers().map(
                    resp -> {
                        provider.setVouchers(resp);
                        return resp;
                    }
            );
        else
            return Observable.just(provider.getVouchers());
    }

    @Override
    public Observable<List<DTOType>> getTypes() {
        if (provider.getTypes().isEmpty())
            return restClient.getTypes().map(
                    resp -> {
                        provider.setTypes(resp);
                        return resp;
                    }
            );
        else
            return Observable.just(provider.getTypes());
    }

    @Override
    public Observable<List<DTOMachine>> getMachinesByLocationId(int locationId) {
        return restClient.getMachinesByLocationId(locationId);
    }

    @Override
    public Observable<List<DTOTime>> getTime() {
        if (provider.getCurrentAppointment().getVoucher().getVoucherId() == 1) {
            if (provider.getEcoTime().isEmpty())
                return restClient.getEcoTime().map(
                            resp -> {
                                provider.setEcoTime(resp);
                                return resp;
                            }
                        );
            else
                return Observable.just(provider.getEcoTime());
        }
        else {
            if (provider.getAllTime().isEmpty())
                return restClient.getAllTime().map(
                            resp -> {
                                provider.setAllTime(resp);
                                return resp;
                            }
                        );
            else
                return Observable.just(provider.getAllTime());
        }
    }

    @Override
    public Observable<List<DTOTime>> getAvailableTime(int dayId, int locationId, int machineId) {
        return restClient.getSelectedTime(dayId, locationId, machineId).map(
                resp -> {
                    List<DTOTime> available = new ArrayList<>();
                    if (provider.getCurrentAppointment().getVoucher().getVoucherId() == 1) {
                        available.addAll(provider.getEcoTime());
                    } else {
                        available.addAll(provider.getAllTime());
                    }
                    for (DTOTime time : resp) {
                        available.removeIf(_time -> _time.getTimeId() == time.getTimeId());
                    }
                    return available;
                }
        );
    }

    @Override
    public Observable<List<DTOWeekDay>> getWeekDays() {
        List<DTOWeekDay> list = new ArrayList<>();
        if (provider.getWeekDays().isEmpty()) {
            return restClient.getDaysOfWeek().map(
                    resp -> {
                        provider.setWeekDays(new ArrayList<>(resp));
                        if (provider.getCurrentAppointment().getVoucher().getVoucherId() == 1) {
                            resp.removeIf(day -> day.getDayId() == 6 || day.getDayId() == 7);
                        }
                        return resp;
                    }
            );
        } else {
            list.addAll(provider.getWeekDays());
            if (provider.getCurrentAppointment().getVoucher().getVoucherId() == 1) {
                list.removeIf(day -> day.getDayId() == 6 || day.getDayId() == 7);
            }
            return Observable.just(list);
        }
    }

    @Override
    public Observable<RepositorySimpleStatus> bookTime(int locationId, DTOAppointmentSchedule appointmentSchedule) {
        UserInfo user = localStorage.getUserFromLocal();
        return restClient.bookTime(user.getToken(), dataMapper.transform(locationId, appointmentSchedule));
    }

    @Override
    public Observable<RepositorySimpleStatus> releaseTime(int locationId, List<DTOAppointmentSchedule> appointmentScheduleList) {
        UserInfo user = localStorage.getUserFromLocal();
        return restClient.releaseTime(user.getToken(), dataMapper.transform(locationId, appointmentScheduleList));
    }

    @Override
    public Observable<RepositorySimpleStatus> validateAppointment() {
        return restClient.validateAppointment();
    }

    @Override
    public Observable<RepositorySimpleStatus> createAppointment(DTOAppointment appointment) {
        UserInfo user = localStorage.getUserFromLocal();
        provider.getCurrentAppointment().setUser(user);
        return restClient.createAppointment(user.getToken(), dataMapper.transform(appointment)).map(
                resp -> {
                    if (!resp.isEmpty()) {
                        List<DTOAppointment> appointmentList = localStorage.getAppointmentsFromLocal();
                        provider.getCurrentAppointment().setAppointmentId(resp);
                        appointmentList.add(provider.getCurrentAppointment());
                        localStorage.saveAppointmentsToLocal(appointmentList);
                        return RepositorySimpleStatus.SUCCESS;
                    }
                    return RepositorySimpleStatus.UNKNOWN_ERROR;
                }
        );
    }

    @Override
    public Observable<RepositorySimpleStatus> confirmAppointment() {
        UserInfo user = localStorage.getUserFromLocal();
        return restClient.confirmAppointment(user.getToken(), user.getId(), this.provider.getCurrentAppointment().getAppointmentId()).map(
                resp -> {
                    if (resp == RepositorySimpleStatus.SUCCESS) {
                        List<DTOAppointment> appointmentList = localStorage.getAppointmentsFromLocal();
                        for (DTOAppointment appointment: appointmentList) {
                            if (appointment.getAppointmentId().equals(this.provider.getCurrentAppointment().getAppointmentId())) {
                                appointment.setStatus(true);
                                break;
                            }
                        }
                        localStorage.saveAppointmentsToLocal(appointmentList);
                        this.provider.setCurrentAppointment(null);
                        return RepositorySimpleStatus.SUCCESS;
                    }
                    return RepositorySimpleStatus.UNKNOWN_ERROR;
                }
        );
    }

    @Override
    public Observable<RepositorySimpleStatus> cancelAppointment() {
        UserInfo user = localStorage.getUserFromLocal();
        return restClient.confirmAppointment(user.getToken(), user.getId(), this.provider.getCurrentAppointment().getAppointmentId()).map(
                resp -> {
                    if (resp == RepositorySimpleStatus.SUCCESS) {
                        List<DTOAppointment> appointmentList = localStorage.getAppointmentsFromLocal();
                        appointmentList.removeIf(appointment -> appointment.getAppointmentId().equals(this.provider.getCurrentAppointment().getAppointmentId()));
                        localStorage.saveAppointmentsToLocal(appointmentList);
                        this.provider.setCurrentAppointment(null);
                        return RepositorySimpleStatus.SUCCESS;
                    }
                    return RepositorySimpleStatus.UNKNOWN_ERROR;
                }
        );
    }

    @Override
    public Observable<List<DTOAppointment>> getAppointments() {
        return Observable.just(localStorage.getAppointmentsFromLocal());
    }
}
