package com.lanthanh.admin.icareapp.domain.model;

/**
 * Created by ADMIN on 06-Jan-17.
 */

public class DTOAppointmentSchedule {
    private int DAY_ID, HOUR_ID, MACHINE_ID;
    private String DAY, HOUR, MACHINE;

    public void setDayId(int id){
        DAY_ID = id;
    }

    public void setHourId(int id){
        HOUR_ID = id;
    }

    public void setMachineId(int id){
        MACHINE_ID = id;
    }

    public void setDayName(String day){
        DAY = day;
    }

    public void setHourName(String hour){
        HOUR = hour;
    }

    public void setMachineName(String machine){
        MACHINE = machine;
    }

    public int getDayId(){
        return DAY_ID;
    }

    public int getHourId(){
        return HOUR_ID;
    }

    public int getMachineId(){
        return MACHINE_ID;
    }

    public String getDayName(){
        return DAY;
    }

    public String getHourName(){
        return HOUR;
    }

    @Override
    public String toString() {
        return DAY + " - " + HOUR + "\n" + MACHINE;
    }
}
