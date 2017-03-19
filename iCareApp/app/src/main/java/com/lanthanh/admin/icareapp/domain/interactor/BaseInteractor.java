package com.lanthanh.admin.icareapp.domain.interactor;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @author longv
 * Created on 19-Mar-17.
 */

public abstract class BaseInteractor<T, Params> {
    private final CompositeDisposable disposables;

    BaseInteractor(){
        disposables = new CompositeDisposable();
    }

    abstract Observable<T> buildUseCaseObservable(Params params);

    public void execute(DisposableObserver<T> observer, Params params){
        disposables.add(
            this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        );
    }

    protected void dispose(){
        if (!disposables.isDisposed()){
            disposables.dispose();
        }
    }
}
