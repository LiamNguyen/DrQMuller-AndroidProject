package com.lanthanh.admin.icareapp.data.manager;

import com.lanthanh.admin.icareapp.domain.model.DTOMachine;

import java.util.List;

/**
 * Created by ADMIN on 04-Feb-17.
 */

public interface MachineManager {
    String MACHINE_ID_KEY = "machine_id";
    List<DTOMachine> getAllMachines(int id);
}
