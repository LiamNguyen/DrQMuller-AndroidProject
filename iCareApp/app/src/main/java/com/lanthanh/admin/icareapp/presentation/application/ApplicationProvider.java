package com.lanthanh.admin.icareapp.presentation.application;

import com.lanthanh.admin.icareapp.presentation.model.dto.DTOAppointment;

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
