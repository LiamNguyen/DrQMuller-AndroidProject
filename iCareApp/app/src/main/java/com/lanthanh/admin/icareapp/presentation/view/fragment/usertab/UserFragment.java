package com.lanthanh.admin.icareapp.presentation.view.fragment.usertab;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.view.adapter.UserTabAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 20-Nov-16.
 */

public class UserFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user, container, false);
        List<String> list = new ArrayList<>();
        SharedPreferences sharedPref = getActivity().getSharedPreferences("content", Context.MODE_PRIVATE);
        list.add(sharedPref.getString("tokenName", getString(R.string.user_name)));
        list.add(getString(R.string.user_option_bag));
        list.add(getString(R.string.user_option_logout));
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.user_recycler_view);
        UserTabAdapter adapter = new UserTabAdapter(getActivity(), list);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
}
