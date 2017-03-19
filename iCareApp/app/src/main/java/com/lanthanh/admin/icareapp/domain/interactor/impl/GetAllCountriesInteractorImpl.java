package com.lanthanh.admin.icareapp.domain.interactor.impl;

import com.lanthanh.admin.icareapp.domain.executor.Executor;
import com.lanthanh.admin.icareapp.threading.MainThread;
import com.lanthanh.admin.icareapp.data.manager.CountryManager;
import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;
import com.lanthanh.admin.icareapp.domain.model.DTOCountry;

import java.util.List;

/**
 * Created by ADMIN on 04-Jan-17.
 */

public class GetAllCountriesInteractorImpl extends AbstractInteractor implements GetAllCountriesInteractor{
    private Callback mCallback;
    private CountryManager mCountryManager;

    public GetAllCountriesInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback, CountryManager cm){
        super(threadExecutor, mainThread);
        mCallback = callback;
        mCountryManager = cm;
    }

    public void init(){

    }

    /*
     *Business logic runs here
     */
    @Override
    public void run() {
        final List<DTOCountry> countryList = mCountryManager.getAllCountries();
        if (countryList == null){
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onNoCountryFound();
                }
            });
        }else {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onAllCountriesReceive(countryList);
                }
            });
        }
    }
}
