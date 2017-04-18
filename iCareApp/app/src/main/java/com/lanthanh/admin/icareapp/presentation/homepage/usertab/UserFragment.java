package com.lanthanh.admin.icareapp.presentation.homepage.usertab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.base.BaseFragment;
import com.lanthanh.admin.icareapp.presentation.adapter.UserTabAdapter;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivity;
import com.lanthanh.admin.icareapp.presentation.homepage.MainActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ADMIN on 20-Nov-16.
 */

public class UserFragment extends BaseFragment<MainActivityPresenter> {
    @BindView(R.id.user_recycler_view) RecyclerView recyclerView;

    private UserTabAdapter adapter;
    private List<String> list;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usertab, container, false);
        unbinder = ButterKnife.bind(this, view);

        initViews();

        return view;
    }

    @Override
    public void initViews() {
        list = new ArrayList<>();
        adapter = new UserTabAdapter(getActivity(), list, getMainPresenter());
        getMainPresenter().populateUserTabOptions(adapter::notifyDataSetChanged, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void refreshViews() { getMainPresenter().populateUserTabOptions(adapter::notifyDataSetChanged, list);}


    @Override
    public MainActivityPresenter getMainPresenter() {
        return ((MainActivity) getActivity()).getMainPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
