package com.example.admin.icareapp.UserInfo;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.admin.icareapp.R;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by ADMIN on 22-Oct-16.
 */

public class UserInfoActivity extends AppCompatActivity implements UserInfoListener{
    private FragmentTransaction frag_transaction;
    private Map<String,String> userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        userInfo = new HashMap<String,String>();

        frag_transaction = getSupportFragmentManager().beginTransaction();
        frag_transaction.add(R.id.ui_fragment_container, new NameAndLocationFragment());
        frag_transaction.addToBackStack(null);
        frag_transaction.commit();
    }

    @Override
    public void addUserInfo(String k, String v) {
        userInfo.put(k, v);
        System.out.println(userInfo.toString());
    }

    @Override
    public boolean checkUserInfo(String k) {
        return userInfo.containsKey(k);
    }

    @Override
    public String getUserInfo(String k) {
        return userInfo.get(k);
    }
}
