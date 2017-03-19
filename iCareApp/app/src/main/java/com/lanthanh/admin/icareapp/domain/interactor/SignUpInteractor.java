package com.lanthanh.admin.icareapp.domain.interactor;

import com.google.gson.JsonObject;
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository;

import io.reactivex.Observable;

/**
 * @author longv
 * Created on 19-Mar-17.
 */

public class SignUpInteractor extends BaseInteractor<JsonObject, SignUpInteractor.Params> {
    private WelcomeRepository welcomeRepository;

    public SignUpInteractor(WelcomeRepository welcomeRepository){
        super();
        this.welcomeRepository = welcomeRepository;
    }

    @Override
    Observable<JsonObject> buildUseCaseObservable(Params params) {
        return welcomeRepository.signup(params.username, params.password);
    }

    public static final class Params{
        private String username, password;

        private Params(String username, String password){
            this.username = username;
            this.password = password;
        }

        public static Params forSignUp(String username, String password){
            return new Params(username, password);
        }
    }
}
