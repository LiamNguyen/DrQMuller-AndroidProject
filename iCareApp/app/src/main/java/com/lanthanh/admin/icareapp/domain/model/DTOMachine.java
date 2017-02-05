package com.lanthanh.admin.icareapp.domain.model;

/**
 * Created by ADMIN on 03-Feb-17.
 */

public class DTOMachine {
    private int MACHINE_ID;
    private String MACHINE_NAME;

    public int getId(){
        return MACHINE_ID;
    }

    public String getMachineName(){
        return MACHINE_NAME;
    }

    @Override
    public String toString() {
        return MACHINE_NAME;
    }
}
