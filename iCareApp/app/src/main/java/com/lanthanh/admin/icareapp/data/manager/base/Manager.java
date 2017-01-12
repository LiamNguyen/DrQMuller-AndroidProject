package com.lanthanh.admin.icareapp.data.manager.base;

/**
 * Created by ADMIN on 04-Jan-17.
 */

public interface Manager {
    boolean isUAT = false;
    boolean insert(Object o);
    boolean delete(Object o);
    boolean update(Object o);
    boolean get(Object o);
}
