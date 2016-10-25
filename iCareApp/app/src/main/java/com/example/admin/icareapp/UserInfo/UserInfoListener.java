package com.example.admin.icareapp.UserInfo;

/**
 * Created by ADMIN on 22-Oct-16.
 */

public interface UserInfoListener {
    void addUserInfo(String k, String v);
    boolean checkUserInfo(String k);
    String getUserInfo(String k);
}
