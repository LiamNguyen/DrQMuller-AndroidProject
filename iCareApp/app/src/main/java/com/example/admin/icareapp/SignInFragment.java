package com.example.admin.icareapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.app.ProgressDialog;
import android.os.AsyncTask;

/**
 * Created by ADMIN on 17-Oct-16.
 */

public class SignInFragment extends Fragment implements View.OnClickListener, DatabaseObserver {
    private EditText login_id;
    private EditText password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        ImageButton back_button = (ImageButton) view.findViewById(R.id.si_back_button);
        Button sign_in_button = (Button) view.findViewById(R.id.si_sign_in_button);
        login_id = (EditText) view.findViewById(R.id.si_login_id_input);
        password = (EditText) view.findViewById(R.id.si_password_input);

        back_button.setOnClickListener(this);
        sign_in_button.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.si_back_button:
                getFragmentManager().popBackStack();
                break;
            case R.id.si_sign_in_button:
                new QueryDatabase(getActivity(), this).execute("authenticate", "http://192.168.0.102/Select_ToAuthenticate.php", login_id.getText().toString(), password.getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void update(Object o) {
        String status = (String) o;

        if (status.equals("1"))
            System.out.println("exist");
        else
            System.out.println("not exist");
    }
}
