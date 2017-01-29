package com.lanthanh.admin.icareapp.presentation.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.airbnb.deeplinkdispatch.DeepLink;

/**
 * Created by ADMIN on 12-Jan-17.
 */

@DeepLink({"icare://210.211.109.180/drmuller/verify", "icare://210.211.109.180/drmuller/restore"})
public class DeepLinkActivity extends Activity {
    public static final String TAG = DeepLinkActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SharedPreferences sharedPref = getSharedPreferences("content", Context.MODE_PRIVATE);
//        if (sharedPref != null){
//            Intent i = new Intent(this, MainActivity.class);
//            startActivity(i);
//            finish();
//        }else {
            Intent intent = getIntent();
            Uri data = intent.getData();
            if (data != null) {
                if (data.getPath().equals("/drmuller/verify") && data.getQueryParameterNames().size() == 1 && data.getQueryParameterNames().contains("cus_id")) {
                    String queryParameter = data.getQueryParameter("cus_id");
                    Intent i = new Intent(this, RegisterActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("cus_id", queryParameter);
                    i.putExtra(TAG, bundle);
                    startActivity(i);
                    finish();
                } else if (data.getPath().equals("/drmuller/restore") && data.getQueryParameterNames().size() == 1 && data.getQueryParameterNames().contains("login_id")) {
                    String queryParameter = data.getQueryParameter("login_id");
                    Intent i = new Intent(this, ResetPasswordActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("login_id", queryParameter);
                    i.putExtra(TAG, bundle);
                    startActivity(i);
                    finish();
                }
            } else {
                Log.e(TAG, "Problem with send email");
            }
//        }
    }
}
