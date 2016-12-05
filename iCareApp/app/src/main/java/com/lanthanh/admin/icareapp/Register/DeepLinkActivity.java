package com.lanthanh.admin.icareapp.Register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.airbnb.deeplinkdispatch.DeepLink;
import com.lanthanh.admin.icareapp.MainActivity;
import com.lanthanh.admin.icareapp.Service.ResetPasswordActivity;

/**
 * Created by ADMIN on 04-Dec-16.
 */
@DeepLink({"icare://210.211.109.180/drmuller/verify", "icare://210.211.109.180/drmuller/restore"})
public class DeepLinkActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getSharedPreferences("content", Context.MODE_PRIVATE);
        if (sharedPref != null){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }else {
            Intent intent = getIntent();
            Uri data = intent.getData();
            if (data != null) {
                if (data.getPath().equals("/drmuller/verify") && data.getQueryParameterNames().size() == 1 && data.getQueryParameterNames().contains("cus_id")) {
                    String queryParameter = data.getQueryParameter("cus_id");
                    Intent i = new Intent(this, RegisterActivity.class);
                    i.putExtra("cus_id", queryParameter);
                    startActivity(i);
                    finish();
                } else if (data.getPath().equals("/drmuller/restore") && data.getQueryParameterNames().size() == 1 && data.getQueryParameterNames().contains("login_id")) {
                    String queryParameter = data.getQueryParameter("login_id");
                    Intent i = new Intent(this, ResetPasswordActivity.class);
                    i.putExtra("login_id", queryParameter);
                    startActivity(i);
                    finish();
                }
            } else {
                System.out.println("Problem with send email");
            }
        }
    }
}
