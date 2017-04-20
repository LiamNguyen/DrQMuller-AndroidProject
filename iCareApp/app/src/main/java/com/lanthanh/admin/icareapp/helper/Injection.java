package com.lanthanh.admin.icareapp.helper;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lanthanh.admin.icareapp.data.repository.UserRepositoryImpl;
import com.lanthanh.admin.icareapp.data.repository.WelcomeRepositoryImpl;
import com.lanthanh.admin.icareapp.domain.interactor.Interactor;
import com.lanthanh.admin.icareapp.domain.repository.UserRepository;
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository;

/**
 * @author longv
 *         Created on 20-Apr-17.
 */

public class Injection {
    public static WelcomeRepository provideWelcomeRepository(@NonNull Context context) {
        return new WelcomeRepositoryImpl(context);
    }

    public static UserRepository provideUserRepository(@NonNull Context context) {
        return new UserRepositoryImpl(context);
    }

    public static Interactor provideUseCaseHandler() {
        return new Interactor();
    }
}
