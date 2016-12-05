package com.lanthanh.admin.icareapp.Register;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.airbnb.deeplinkdispatch.DeepLink;
import com.lanthanh.admin.icareapp.MainActivity;
import com.lanthanh.admin.icareapp.Model.ModelURL;

/**
 * Created by ADMIN on 04-Dec-16.
 */
@DeepLink("icare://210.211.109.180/drmuller/verify")
public class DeepLinkActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Uri data = intent.getData();
        if (data != null) {
            if (data.getPath().equals("/drmuller/verify") && data.getQueryParameterNames().size() == 1 && data.getQueryParameterNames().contains("cus_id")) {
                String queryParameter = data.getQueryParameter("cus_id");
                Intent i = new Intent(this, RegisterActivity.class);
                i.putExtra("cus_id", queryParameter);
                startActivity(i);
                finish();
            }
        } else {
            System.out.println("Problem with send email verify acc");
        }
    }
}
