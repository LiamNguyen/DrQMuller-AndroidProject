package com.lanthanh.admin.icareapp.domain.interactor;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author longv
 * Created on 19-Mar-17.
 */

public abstract class BaseInteractorv2<T> {
    private final CompositeDisposable disposables;

    BaseInteractorv2(){
        disposables = new CompositeDisposable();
    }

    abstract Observable<T> buildUseCaseObservable();

    public void execute(Consumer<T> onNext, Consumer<Throwable> onError){
        disposables.add(
                this.buildUseCaseObservable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onNext, onError)
        );
    }

    protected void dispose(){
        if (!disposables.isDisposed()){
            disposables.dispose();
        }
    }
}

