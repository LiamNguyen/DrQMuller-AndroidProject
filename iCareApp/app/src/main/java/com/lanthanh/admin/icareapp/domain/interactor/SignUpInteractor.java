package com.lanthanh.admin.icareapp.domain.interactor;

import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository;

import io.reactivex.Observable;

/**
 * @author longv
 * Created on 19-Mar-17.
 */

public class SignUpInteractor extends BaseInteractor<RepositorySimpleStatus, SignUpInteractor.Params> {
    private WelcomeRepository welcomeRepository;

    public SignUpInteractor(WelcomeRepository welcomeRepository){
        super();
        this.welcomeRepository = welcomeRepository;
    }

    @Override
    Observable<RepositorySimpleStatus> buildUseCaseObservable(Params params) {
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
