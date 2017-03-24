package com.lanthanh.admin.icareapp.data.manager;

import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.presentation.model.DTOAppointment;

/**
 * Created by ADMIN on 10-Jan-17.
 */

public interface SendEmailManager extends Manager {
    int  STATUS_SENT = 1;
    int  STATUS_NOTSENT = -1;
    int  STATUS_USERNAMEOREMAIL_NOTFOUND = 0;
    int sendEmailNotifyBooking(DTOAppointment dtoAppointment);
    int sendEmailVerifyAcc(String email, int id);
    int sendEmailResetPassword(String username);
}
