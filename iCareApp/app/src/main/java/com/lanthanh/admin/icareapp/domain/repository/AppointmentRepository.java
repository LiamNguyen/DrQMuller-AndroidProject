package com.lanthanh.admin.icareapp.domain.repository;

import com.lanthanh.admin.icareapp.presentation.model.DTOAppointment;
import com.lanthanh.admin.icareapp.presentation.model.DTOAppointmentSchedule;
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
    Observable<List<DTOTime>> getAllTime();
    Observable<List<DTOTime>> getSelectedTime(int dayId, int locationId, int machineId);
    Observable<List<DTOTime>> getEcoTime();
    Observable<List<DTOWeekDay>> getWeekDays();
    Observable<RepositorySimpleStatus> bookTime(String authToken, int locationdId, List<DTOAppointmentSchedule> appointmentScheduleList);
    Observable<RepositorySimpleStatus> releaseTime(String authToken, int locationId, List<DTOAppointmentSchedule> appointmentScheduleList);
    Observable<RepositorySimpleStatus> validateAppointment();
    Observable<String> createAppointment(String authToken, DTOAppointment appointment);
    Observable<RepositorySimpleStatus> confirmAppointment(String authToken, int userId, int appointmentId);
    Observable<RepositorySimpleStatus> cancelAppointment(String authToken, int userId, int appointmentId);
}
