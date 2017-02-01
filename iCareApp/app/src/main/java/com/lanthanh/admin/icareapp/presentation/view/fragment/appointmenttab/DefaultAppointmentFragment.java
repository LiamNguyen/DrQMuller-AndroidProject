package com.lanthanh.admin.icareapp.presentation.view.fragment.appointmenttab;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.presenter.MainActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.view.activity.MainActivity;
import com.lanthanh.admin.icareapp.utils.GraphicUtils;

/**
 * Created by ADMIN on 24-Jan-17.
 */

public class DefaultAppointmentFragment extends Fragment implements View.OnClickListener{
    private MainActivityPresenter mainActivityPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_defaultappointment, container, false);

        init();

        //Get finish button
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GraphicUtils.FONT_LIGHT);//Custom font
        AppCompatButton button = (AppCompatButton) view.findViewById(R.id.button);
        button.setOnClickListener(this);
        button.setTypeface(font);

        return view;
    }

    public void init(){
        //Init presenter
        mainActivityPresenter = ((MainActivity) getActivity()).getMainPresenter();
    }

    @Override
    public void onClick(View view) {
        mainActivityPresenter.navigateToBookingActivity();
    }
}
