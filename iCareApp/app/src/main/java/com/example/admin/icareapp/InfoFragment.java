package com.example.admin.icareapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by ADMIN on 17-Oct-16.
 */

public class InfoFragment extends Fragment implements View.OnClickListener{
    private final String first_name_requirement = "[a-zA-Z]+";
    private final String last_name_requirement = "[a-zA-Z]+";
    private final String email_requirement = "[\\w]+@[^@]*[^@]$";
    private final String phone_requirement = "\\d{1,16}";
    private final String address_requirement = "\\w+";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        ImageButton back_button = (ImageButton) view.findViewById(R.id.info_back_button);
        Button sign_up_button = (Button) view.findViewById(R.id.info_sign_up_button);

        back_button.setOnClickListener(this);
        sign_up_button.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.info_back_button:
                getFragmentManager().popBackStack();
                break;
            case R.id.info_sign_up_button:
                break;
            default:
                break;
        }
    }
}
