package com.lanthanh.admin.icareapp.domain.interactor;

import com.google.gson.JsonObject;

import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository;

import io.reactivex.Observable;

/**
 * @author longv
 * Created on 19-Mar-17.
 */

public class UpdateCustomerBasicInfoInteractor extends BaseInteractor<RepositorySimpleStatus, UpdateCustomerBasicInfoInteractor.Params>{
    private WelcomeRepository welcomeRepository;

    public UpdateCustomerBasicInfoInteractor(WelcomeRepository welcomeRepository){
        super();
        this.welcomeRepository = welcomeRepository;
    }

    @Override
    Observable<RepositorySimpleStatus> buildUseCaseObservable(Params params) {
        return welcomeRepository.updateCustomerBasicInfo(params.name, params.address);
    }

    public static final class Params {
        private String name, address;

        private Params(String name, String address){
            this.name = name;
            this.address = address;
        }

        public static Params forUpdateCustomerBasicInfo(String name, String address){
            return new Params(name, address);
        }
    }

}
