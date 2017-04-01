package com.lanthanh.admin.icareapp.presentation.model.dto;

import com.lanthanh.admin.icareapp.presentation.model.dto.DTOMachine;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOTime;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOWeekDay;

/**
 * Created by ADMIN on 06-Jan-17.
 */

public class DTOAppointmentSchedule {
    private DTOWeekDay bookedDay;
    private DTOTime bookedTime;
    private DTOMachine bookedMachine;

    public DTOWeekDay getBookedDay() {
        return bookedDay;
    }

    public void setBookedDay(DTOWeekDay bookedDay) {
        this.bookedDay = bookedDay;
    }

    public DTOTime getBookedTime() {
        return bookedTime;
    }

    public void setBookedTime(DTOTime bookedTime) {
        this.bookedTime = bookedTime;
    }

    public DTOMachine getBookedMachine() {
        return bookedMachine;
    }

    public void setBookedMachine(DTOMachine bookedMachine) {
        this.bookedMachine = bookedMachine;
    }

    @Override
    public String toString() {
        return bookedDay.getDayName() + " - " + bookedTime.getTime() + "\n" + bookedMachine.getMachineName();
    }
}
