package com.lanthanh.admin.icareapp.domain.interactor;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
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

    public void execute(Consumer<T> onNext, Consumer<Throwable> onError, Action onComplete, Params params){
        disposables.add(
            this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError, onComplete)
        );
    }

    protected void dispose(){
        if (!disposables.isDisposed()){
            disposables.dispose();
        }
    }
}
