package com.lanthanh.admin.icareapp.Model;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.lanthanh.admin.icareapp.Controller.Controller;

/**
 * Created by ADMIN on 19-Oct-16.
 */

public class BackgroundTask extends AsyncTask<String,Void,Object>{
    private ProgressDialog pDialog;
    private Context context;
    private DatabaseObserver observer;
    private Controller aController;

    public BackgroundTask(Context context, DatabaseObserver observer){
        this.context = context;
        this.observer = observer;
        aController = Controller.getInstance();
    }
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        pDialog = new ProgressDialog(context);
//        pDialog.setMessage("Loading Data ...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(false);
//        pDialog.show();
//    }

    @Override
    protected Object doInBackground(String... params){
        Object obj;

        aController.getDatabaseQuery().getURL(params[0]);
        obj = aController.getDatabaseQuery().sendRequest(params[1]);

        /*if (params[0].equals("authenticate") || params[0].equals("insert_user")) {
            aController.getDatabaseQuery().getURL(params[1]);
            obj = aController.getDatabaseQuery().getAuthenticateAndInsertNewUser(params[2], params[3]);
        }else if (params[0].equals("check_user")) {
            aController.getDatabaseQuery().getURL(params[1]);
            obj = aController.getDatabaseQuery().checkUserExistence(params[2]);
        }else if (params[0].equals("update_user")){
            aController.getDatabaseQuery().getURL(params[1]);
            obj = aController.getDatabaseQuery().updateUser(params[2]);
        }else if (params[0].equals("num_users")){
            aController.getDatabaseQuery().getURL(params[1]);
            obj = aController.getDatabaseQuery().getNumberOfCustomers();
        }*/

        return obj;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
//        pDialog.dismiss();
        if (o != null)
            observer.update(o);
        else
            System.out.println("Cannot query from database");
    }
}
