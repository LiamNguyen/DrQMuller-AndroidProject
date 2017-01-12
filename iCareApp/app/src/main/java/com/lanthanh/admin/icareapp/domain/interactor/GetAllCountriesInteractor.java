package com.lanthanh.admin.icareapp.domain.interactor;

import com.lanthanh.admin.icareapp.domain.interactor.base.Interactor;
import com.lanthanh.admin.icareapp.domain.model.DTOCountry;

import java.util.List;

/**
 * Created by ADMIN on 04-Jan-17.
 */

public interface GetAllCountriesInteractor extends Interactor {
    interface Callback{
        void onNoCountryFound();
        void onAllCountriesReceive(List<DTOCountry> countryList);
    }
}
