package com.lanthanh.admin.icareapp.BookingTab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.Register.RegisterActivity;

/**
 * Created by ADMIN on 21-Nov-16.
 */

public class BookingNotSignedIn extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_notsingedin, container, false);

        AppCompatButton button = (AppCompatButton) view.findViewById(R.id.booking_toSignIn_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toRegister = new Intent(getActivity(), RegisterActivity.class);
                startActivity(toRegister);
            }
        });

        return view;
    }
}
