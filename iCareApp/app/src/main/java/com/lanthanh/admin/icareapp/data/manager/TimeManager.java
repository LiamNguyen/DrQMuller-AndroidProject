package com.lanthanh.admin.icareapp.data.manager;

import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTOTime;
import com.lanthanh.admin.icareapp.threading.MainThread;

import java.util.List;

/**
 * Created by ADMIN on 07-Jan-17.
 */

public interface TimeManager extends Manager{
    String TIME_ID_KEY = "time_id";
    List<DTOTime> getAllTime();
    List<DTOTime> getAllSelectedTime(int dayId, int locationId, int machineId);
    List<DTOTime> getAllEcoTime();
}
