package com.lanthanh.admin.icareapp.data.service;

/**
 * Created by ADMIN on 19-Feb-17.
 */

public interface Service {
    class Status{
        public final static String SUCCESS = "success";
        public final static String EXISTED = "existed";
        public final static String FAILED = "failed";
        public final static String UNAUTHORIZED = "unauthorized";
        public final static String INTERNAL_ERROR = "internal_error";
    }
}
