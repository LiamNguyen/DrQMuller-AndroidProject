package com.lanthanh.admin.icareapp.data.manager;

import com.lanthanh.admin.icareapp.data.manager.base.Manager;
import com.lanthanh.admin.icareapp.domain.model.DTOType;

import java.util.List;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public interface TypeManager extends Manager{
    String TYPE_ID_KEY = "type";
    String TYPE_NAME_KEY = "type";
    List<DTOType> getAllTypes();
}
