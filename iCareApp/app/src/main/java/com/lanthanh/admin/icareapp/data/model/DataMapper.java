package com.lanthanh.admin.icareapp.data.model;

import com.lanthanh.admin.icareapp.presentation.model.DTOAppointment;
import com.lanthanh.admin.icareapp.presentation.model.DTOAppointmentSchedule;

import java.util.ArrayList;
import java.util.List;

/**
 * @author longv
 *         Created on 24-Mar-17.
 */

public class DataMapper {

    public DataMapper() {}

    public BookedTime transform(int dayId, int timeId, int machineId) {
        return new BookedTime(dayId, timeId, machineId);
    }

    public BookedAppointment transform(DTOAppointment appointment) {
        List<BookedTime> bookedTimes = new ArrayList<>();
        for (DTOAppointmentSchedule appointmentSchedule: appointment.getAppointmentScheduleList()) {
            bookedTimes.add(new BookedTime(appointmentSchedule.getBookedDay().getDayId(), appointmentSchedule.getBookedTime().getTimeId(), appointmentSchedule.getBookedMachine().getMachineId()));
        }
        return new BookedAppointment(
                appointment.getUser().getId(),
                appointment.getStartDate(),
                appointment.getExpireDate(),
                appointment.getType().getTypeId(),
                appointment.getLocation().getLocationId(),
                appointment.getVoucher().getVoucherId(),
                appointment.getVerificationCode(),
                bookedTimes
        );
    }
}
