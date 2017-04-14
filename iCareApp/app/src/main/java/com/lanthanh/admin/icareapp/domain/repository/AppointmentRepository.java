package com.lanthanh.admin.icareapp.domain.repository;

import com.lanthanh.admin.icareapp.presentation.model.dto.DTOAppointment;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOAppointmentSchedule;
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

/**
 * Created by long.vu on 3/23/2017.
 */

public interface AppointmentRepository {
    Observable<List<DTOCountry>> getCountries();
    Observable<List<DTOCity>> getCitiesByCountryId(int countryId);
    Observable<List<DTODistrict>> getDistrictsByCityId(int cityId);
    Observable<List<DTOLocation>> getLocationsByDistrictId(int districtId);
    Observable<List<DTOVoucher>> getVouchers();
    Observable<List<DTOType>> getTypes();
    Observable<List<DTOMachine>> getMachinesByLocationId(int locationId);
    Observable<List<DTOTime>> getTime(int voucherId);
    Observable<List<DTOTime>> getAvailableTime(int dayId, int locationId, int machineId, int voucherId);
    Observable<List<DTOWeekDay>> getWeekDays(int voucherId);
    Observable<RepositorySimpleStatus> bookTime(int locationId, DTOAppointmentSchedule appointmentSchedule);
    Observable<RepositorySimpleStatus> releaseTime(int locationId, List<DTOAppointmentSchedule> appointmentScheduleList);
    Observable<RepositorySimpleStatus> validateAppointment();
    Observable<RepositorySimpleStatus> createAppointment(DTOAppointment appointment);
    Observable<RepositorySimpleStatus> confirmAppointment(String appointmentId);
    Observable<RepositorySimpleStatus> cancelAppointment(String appointmentId);
    Observable<List<DTOAppointment>> getAppointments();
    Observable<RepositorySimpleStatus> sendEmailNotifyBooking(String appointmentId);
}
