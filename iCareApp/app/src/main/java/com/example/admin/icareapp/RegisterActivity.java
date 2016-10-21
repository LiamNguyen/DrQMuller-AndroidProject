package com.example.admin.icareapp;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
/**
 * Created by ADMIN on 17-Oct-16.
 */

public class RegisterActivity extends AppCompatActivity{
    private FragmentTransaction frag_transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        frag_transaction = getFragmentManager().beginTransaction();
        frag_transaction.add(R.id.wel_fragment_container, new RegisterFragment());
        frag_transaction.commit();
    }


}
