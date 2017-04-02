package com.lanthanh.admin.icareapp.data.model;

import com.lanthanh.admin.icareapp.utils.converter.ConverterForDisplay;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOAppointment;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOAppointmentSchedule;

import java.util.ArrayList;
import java.util.List;

/**
 * @author longv
 *         Created on 24-Mar-17.
 */

public class DataMapper {

    public DataMapper() {}

    public BookedSchedule transform(int locationId, DTOAppointmentSchedule appointmentSchedule) {
        List<BookedSchedule.BookedTime> list =  new ArrayList<>();
        list.add(new BookedSchedule.BookedTime(appointmentSchedule.getBookedDay().getDayId(), appointmentSchedule.getBookedTime().getTimeId(), appointmentSchedule.getBookedMachine().getMachineId()));
        return new BookedSchedule(locationId, list);
    }

    public BookedSchedule transform(int locationId, List<DTOAppointmentSchedule> appointmentSchedules) {
        List<BookedSchedule.BookedTime> list =  new ArrayList<>();

        for (DTOAppointmentSchedule schedule: appointmentSchedules) {
            list.add(new BookedSchedule.BookedTime(schedule.getBookedDay().getDayId(), schedule.getBookedTime().getTimeId(), schedule.getBookedMachine().getMachineId()));
        }

        return new BookedSchedule(locationId, list);
    }

    public BookedAppointment transform(DTOAppointment appointment) {
        List<BookedSchedule.BookedTime> list =  new ArrayList<>();

        for (DTOAppointmentSchedule appointmentSchedule: appointment.getAppointmentScheduleList()) {
            list.add(new BookedSchedule.BookedTime(appointmentSchedule.getBookedDay().getDayId(), appointmentSchedule.getBookedTime().getTimeId(), appointmentSchedule.getBookedMachine().getMachineId()));
        }
        return new BookedAppointment(
                appointment.getUser().getId(),
                ConverterForDisplay.convertDateForDb(appointment.getStartDate()),
                ConverterForDisplay.convertDateForDb(appointment.getExpireDate()),
                appointment.getType().getTypeId(),
                appointment.getLocation().getLocationId(),
                appointment.getVoucher().getVoucherId(),
                appointment.getVerificationCode(),
                list
        );
    }
}
