package com.lanthanh.admin.icareapp.presentation.base;

import com.lanthanh.admin.icareapp.domain.model.DTOAppointment;

import java.util.Date;

/**
 * Created by ADMIN on 31-Dec-16.
 */

public interface Presenter {
    void resume();
    void pause();
    void stop();
    void destroy();
    void onError(String message);
}
