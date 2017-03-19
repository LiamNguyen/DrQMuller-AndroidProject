package com.lanthanh.admin.icareapp.data.restapi;

import okhttp3.RequestBody;

/**
 * Created by ADMIN on 19-Feb-17.
 */

public interface RestClient {
    <T> T createService(Class<T> classOfT);
    RequestBody createRequestBody(String[] keys, String[] values);
}
