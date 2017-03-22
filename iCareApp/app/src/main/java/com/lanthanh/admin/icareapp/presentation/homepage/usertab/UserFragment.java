package com.lanthanh.admin.icareapp.presentation.homepage.usertab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.api.impl.iCareApiImpl;
import com.lanthanh.admin.icareapp.data.manager.CustomerManager;
import com.lanthanh.admin.icareapp.data.manager.impl.CustomerManagerImpl;
import com.lanthanh.admin.icareapp.presentation.model.ModelUser;
import com.lanthanh.admin.icareapp.presentation.presenter.MainActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivity;
import com.lanthanh.admin.icareapp.presentation.adapter.UserTabAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 20-Nov-16.
 */

public class UserFragment extends Fragment{
    private MainActivityPresenter mainActivityPresenter;
    private List<String> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usertab, container, false);

        init();

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.user_recycler_view);
        UserTabAdapter adapter = new UserTabAdapter(getActivity(), list, mainActivityPresenter);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    public void init(){
        //Init presenter
        mainActivityPresenter = ((MainActivity) getActivity()).getMainPresenter();
        //Get user to gather info
        CustomerManager customerManager = new CustomerManagerImpl(iCareApiImpl.getAPI());
        ModelUser mUser = customerManager.getLocalUserFromPref(mainActivityPresenter.getLocalStorage());
        //Init options list
        list = new ArrayList<>();
        list.add(mUser.getName());
        //list.add(getString(R.string.user_option_bag));
        list.add(getString(R.string.user_option_logout));
    }
}
