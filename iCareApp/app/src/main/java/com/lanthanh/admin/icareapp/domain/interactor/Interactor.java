package com.lanthanh.admin.icareapp.domain.interactor;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by long.vu on 3/23/2017.
 */

public class Interactor {
    private final CompositeDisposable disposables;

    public Interactor(){
        disposables = new CompositeDisposable();
    }

    public <T> void execute(BuildUseCase<T> buildUseCase, Consumer<T> onNext, Consumer<Throwable> onError){
        disposables.add(
            buildUseCase.build()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError)
        );
    }

    public void dispose(){
        if (!disposables.isDisposed()){
            disposables.dispose();
        }
    }

    public interface BuildUseCase<T>{
        Observable<T> build();
    }
}
