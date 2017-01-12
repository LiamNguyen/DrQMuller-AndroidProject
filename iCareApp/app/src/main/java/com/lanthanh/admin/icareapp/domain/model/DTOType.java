package com.lanthanh.admin.icareapp.domain.model;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public class DTOType {
    private int TYPE_ID;
    private String TYPE;

    public int getId(){
        return TYPE_ID;
    }

    public String getTypeName(){
        return TYPE;
    }

    @Override
    public String toString() {
        return TYPE;
    }
}
