package com.lanthanh.admin.icareapp.UserInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.Controller.Controller;
import com.lanthanh.admin.icareapp.MainActivity;
import com.lanthanh.admin.icareapp.Model.DatabaseObserver;
import com.lanthanh.admin.icareapp.Model.ModelURL;
import com.lanthanh.admin.icareapp.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ADMIN on 08-Nov-16.
 */

public class ValidateFragment extends Fragment implements View.OnClickListener, DatabaseObserver{
    private Controller aController = Controller.getInstance();
    private TextView noti;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        aController.setRequestData(getActivity(), this, ModelURL.SEND_EMAIL.getUrl(MainActivity.isUAT), aController.getUserInfo().getPostEmail());

        View view = inflater.inflate(R.layout.userinfo_validate, container, false);

        //ImageButton back = (ImageButton) view.findViewById(R.id.back_button);
        //back.setOnClickListener(this);
        AppCompatButton resend = (AppCompatButton) view.findViewById(R.id.ui_resend_email_button);
        resend.setOnClickListener(this);
        AppCompatButton change = (AppCompatButton) view.findViewById(R.id.ui_change_email_button);
        change.setOnClickListener(this);
        AppCompatButton back = (AppCompatButton) view.findViewById(R.id.ui_back_to_register);
        back.setOnClickListener(this);
        noti = (TextView) view.findViewById(R.id.ui_validate_noti);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ui_resend_email_button:
                aController.setRequestData(getActivity(), this, ModelURL.SEND_EMAIL.getUrl(MainActivity.isUAT), aController.getUserInfo().getPostEmail());
                break;
            case R.id.ui_change_email_button:
                ((UserInfoActivity) getActivity()).navigateToChangeEmail();
                break;
            case R.id.ui_back_to_register:
                ((UserInfoActivity) getActivity()).navigateToRegister();
                break;
            default:
                break;
        }
    }

    @Override
    public void update(Object o) {
        JSONObject status = (JSONObject) o;

        try {
            if (status.has("Send_Email")){
                String result = status.getString("Send_Email");
                if (result.equals("Message has been sent")){
                    noti.setText(R.string.validate_noti_success);
                    noti.setTextColor(getResources().getColor(R.color.colorGreen));
                }else{
                    System.out.println("Message could not be sent");
                    System.out.println(status.getString("ERROR"));
                    noti.setText(R.string.validate_noti_fail);
                    noti.setTextColor(getResources().getColor(R.color.colorLightRed));

                }
            }
        } catch (JSONException je){
            System.out.println("Problem with JSON API");
        }
    }
}
