package com.lanthanh.admin.icareapp.domain.model;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public class DTOTime {
    private int TIME_ID;
    private String TIME;

    public int getId(){
        return TIME_ID;
    }

    public String getTimeName(){
        return TIME;
    }

    @Override
    public String toString() {
        return TIME;
    }
}
