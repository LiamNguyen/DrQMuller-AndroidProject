package com.lanthanh.admin.icareapp.Controller;

import android.content.Context;

import com.lanthanh.admin.icareapp.Model.BackgroundTask;
import com.lanthanh.admin.icareapp.Model.DatabaseObserver;
import com.lanthanh.admin.icareapp.Model.DatabaseQuery;
import com.lanthanh.admin.icareapp.Model.ModelAccount;
import com.lanthanh.admin.icareapp.Model.ModelUserInfo;

/**
 * Created by ADMIN on 25-Oct-16.
 */
public class Controller {
    private ModelUserInfo userInfo;
    private ModelAccount account;
    private DatabaseQuery databaseQuery;

    private static Controller ourInstance = new Controller();

    public static Controller getInstance() {
        return ourInstance;
    }

    private Controller() {
        userInfo = new ModelUserInfo();
        account = new ModelAccount();
        databaseQuery = new DatabaseQuery();
    }

    public ModelUserInfo getUserInfo(){
        return userInfo;
    }

    public ModelAccount getAccount(){
        return account;
    }

    public DatabaseQuery getDatabaseQuery(){
        return databaseQuery;
    }

    public void setRequestData(Context ctxt, DatabaseObserver ob, String url, String data){
        new BackgroundTask(ctxt, ob).execute(url, data);
    }
}
