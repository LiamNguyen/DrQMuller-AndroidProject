package com.lanthanh.admin.icareapp.data.manager;

import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTOWeekDay;

import java.util.List;

/**
 * Created by ADMIN on 07-Jan-17.
 */

public interface WeekDayManager extends Manager{
    String DAY_ID_KEY = "day_id";
    List<DTOWeekDay> getAllWeekDays();
}
