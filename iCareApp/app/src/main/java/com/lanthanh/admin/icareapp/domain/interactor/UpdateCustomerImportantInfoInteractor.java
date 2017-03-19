package com.lanthanh.admin.icareapp.domain.interactor;

import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository;

import io.reactivex.Observable;

/**
 * @author longv
 * Created on 19-Mar-17.
 */

public class UpdateCustomerImportantInfoInteractor extends BaseInteractor<JsonObject, UpdateCustomerImportantInfoInteractor.Params> {
    private WelcomeRepository welcomeRepository;

    public UpdateCustomerImportantInfoInteractor(WelcomeRepository welcomeRepository){
        super();
        this.welcomeRepository = welcomeRepository;
    }

    @Override
    Observable<JsonObject> buildUseCaseObservable(Params params) {
        return welcomeRepository.updateCustomerImportantInfo(params.email, params.phone);
    }

    public static final class Params {
        String email, phone;

        private Params(String email, String phone){
            this.email = email;
            this.phone = phone;
        }

        public static Params forUpdateCustomerImportantInfo(String email, String phone){
            return new Params(email, phone);
        }
    }
}
