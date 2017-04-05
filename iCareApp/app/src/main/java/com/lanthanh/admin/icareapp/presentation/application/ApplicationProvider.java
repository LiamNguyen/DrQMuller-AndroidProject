package com.lanthanh.admin.icareapp.presentation.application;


import com.lanthanh.admin.icareapp.presentation.model.dto.DTOAppointment;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOCity;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOCountry;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTODistrict;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOLocation;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOMachine;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOTime;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOType;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOVoucher;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOWeekDay;

import java.util.ArrayList;
import java.util.List;

/**
 * @author longv
 *         Created on 23-Mar-17.
 */

public class ApplicationProvider {
    private DTOAppointment currentAppointment;

    public ApplicationProvider() {}

    public DTOAppointment getCurrentAppointment() {
        if (currentAppointment == null) {
            currentAppointment = new DTOAppointment();
        }
        return currentAppointment;
    }

    public void setCurrentAppointment(DTOAppointment currentAppointment) {
        this.currentAppointment = currentAppointment;
    }
}
