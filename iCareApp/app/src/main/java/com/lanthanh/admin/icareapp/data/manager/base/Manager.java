package com.lanthanh.admin.icareapp.data.manager.base;

/**
 * Created by ADMIN on 04-Jan-17.
 */

public interface Manager {
    int DB_PRD = 1;
    int DB_BETA = 2;
    int DB_UAT = 3;
    int DB_TYPE = DB_BETA;
    boolean insert(Object o);
    boolean delete(Object o);
    boolean update(Object o);
    boolean get(Object o);
}
