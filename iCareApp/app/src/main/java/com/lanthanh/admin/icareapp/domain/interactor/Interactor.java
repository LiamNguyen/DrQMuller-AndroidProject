package com.lanthanh.admin.icareapp.domain.interactor;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by long.vu on 3/23/2017.
 */

public class Interactor<T>{
    private final CompositeDisposable disposables;
    private BuildUseCase<T> buildUseCase;

    protected Interactor(BuildUseCase<T> buildUseCase){
        this.buildUseCase = buildUseCase;
        disposables = new CompositeDisposable();
    }

    public void execute(Consumer<T> onNext, Consumer<Throwable> onError){
        disposables.add(
                this.buildUseCase.build()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onNext, onError)
        );
    }

    private void dispose(){
        if (!disposables.isDisposed()){
            disposables.dispose();
        }
    }

    public interface BuildUseCase<T>{
        Observable<T> build();
    }
}
