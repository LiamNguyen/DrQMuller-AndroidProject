package com.lanthanh.admin.icareapp.api;

/**
 * Created by ADMIN on 13-Feb-17.
 */

public interface RestClient {
    <T> T createService(Class<T> classOfT);
}
