package com.lanthanh.admin.icareapp.data.manager;

import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTOAppointment;

/**
 * Created by ADMIN on 10-Jan-17.
 */

public interface SendEmailManager extends Manager {
    boolean sendEmailNotifyBooking(DTOAppointment dtoAppointment);
    boolean sendEmailResetPassword(String email, String username);
}
