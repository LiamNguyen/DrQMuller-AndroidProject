package com.lanthanh.admin.icareapp.presentation.model.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by long.vu on 3/22/2017.
 */

public class DTOMachine {
    @SerializedName("MACHINE_ID") private int machineId;
    @SerializedName("MACHINE_NAME") private String machineName;

    public DTOMachine(int machineId, String machineName) {
        this.machineId = machineId;
        this.machineName = machineName;
    }

    public int getMachineId() {
        return machineId;
    }

    public String getMachineName() {
        return machineName;
    }
}
