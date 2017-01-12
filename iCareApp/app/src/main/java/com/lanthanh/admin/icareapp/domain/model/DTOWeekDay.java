package com.lanthanh.admin.icareapp.domain.model;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public class DTOWeekDay {
    private int DAY_ID;
    private String DAY;

    public int getId(){
        return DAY_ID;
    }

    public String getDayName(){
        return DAY;
    }

    @Override
    public String toString() {
        return DAY;
    }
}
