package com.example.admin.icareapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by ADMIN on 18-Oct-16.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener{
    private FragmentTransaction frag_transaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        Button sign_in_button = (Button) view.findViewById(R.id.wel_sign_in_button);
        Button sign_up_button = (Button) view.findViewById(R.id.wel_sign_up_button);

        sign_in_button.setOnClickListener(this);
        sign_up_button.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.wel_sign_in_button:
                frag_transaction = getFragmentManager().beginTransaction();
                frag_transaction.replace(R.id.wel_fragment_container, new SignInFragment());
                frag_transaction.addToBackStack(null);
                frag_transaction.commit();
                break;
            case R.id.wel_sign_up_button:
                frag_transaction = getFragmentManager().beginTransaction();
                frag_transaction.replace(R.id.wel_fragment_container, new SignUpFragment());
                frag_transaction.addToBackStack(null);
                frag_transaction.commit();
                break;
            default:
                break;
        }
    }
}
