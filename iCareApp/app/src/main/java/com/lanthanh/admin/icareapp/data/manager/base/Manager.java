package com.lanthanh.admin.icareapp.data.manager.base;

/**
 * Created by ADMIN on 04-Jan-17.
 */

public interface Manager {
    int DB_PRD = 1;
    int DB_UAT = 2;
    int BLUE_HOST = 3;
    int DB_TYPE = DB_UAT;
    boolean insert(Object o);
    boolean delete(Object o);
    boolean update(Object o);
    boolean get(Object o);
}
