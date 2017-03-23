package com.lanthanh.admin.icareapp.domain.interactor;

/**
 * Created by long.vu on 3/23/2017.
 */

public class InteractorFactory {
    public static <T> Interactor<T> create(Interactor.BuildUseCase<T> buildUseCase){
        return new Interactor<>(buildUseCase);
    }
}
