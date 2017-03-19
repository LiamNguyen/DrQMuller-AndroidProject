package com.lanthanh.admin.icareapp.domain.interactor;

import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository;

import io.reactivex.Observable;

/**
 * @author longv
 * Created on 19-Mar-17.
 */

public class UpdateCustomerNecessaryInfoInteractor extends BaseInteractor<JsonObject, UpdateCustomerNecessaryInfoInteractor.Params> {
    private WelcomeRepository welcomeRepository;

    public UpdateCustomerNecessaryInfoInteractor(WelcomeRepository welcomeRepository){
        super();
        this.welcomeRepository = welcomeRepository;
    }

    @Override
    Observable<JsonObject> buildUseCaseObservable(Params params) {
        return welcomeRepository.updateCustomerNecessaryInfo(params.dob, params.gender);
    }

    public static final class Params {
        private String dob, gender;

        private Params(String dob, String gender){
            this.dob = dob;
            this.gender = gender;
        }

        public static Params forUpdateCustomerNecessaryInfo(String dob, String gender){
            return new Params(dob, gender);
        }
    }
}
