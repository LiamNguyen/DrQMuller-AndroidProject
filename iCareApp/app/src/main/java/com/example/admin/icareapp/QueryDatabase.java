package com.example.admin.icareapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by ADMIN on 19-Oct-16.
 */

public class QueryDatabase extends AsyncTask<String,Void,Object>{
    private ProgressDialog pDialog;
    private Context context;
    private DatabaseObserver observer;

    public QueryDatabase(Context context, DatabaseObserver observer){
        this.context = context;
        this.observer = observer;
    }

    /*@Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading Data ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }*/

    @Override
    protected Object doInBackground(String... params) {
        Object obj = null;

        if (params[0].equals("authenticate") || params[0].equals("insert_user")) {
            JSONParser.getInstance().getURL(params[1]);
            obj = JSONParser.getInstance().getAuthenticateAndInsertNewUser(params[2], params[3]);
        }else if (params[0].equals("checkuser")) {
            JSONParser.getInstance().getURL(params[1]);
            obj = JSONParser.getInstance().checkUserExistence(params[2]);
        }

        return obj;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        //pDialog.dismiss();
        if (o != null)
            observer.update(o);
        else
            System.out.println("Cannot query from database");
    }
}
