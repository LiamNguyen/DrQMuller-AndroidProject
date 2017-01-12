package com.lanthanh.admin.icareapp.domain.executor;

import com.lanthanh.admin.icareapp.domain.interactor.base.AbstractInteractor;

/**
 * Created by ADMIN on 05-Jan-17.
 */

public interface Executor {
    /**
     * This method should call the interactor's run method and thus start the interactor. This should be called
     * on a background thread as interactors might do lengthy operations.
     *
     * @param interactor The interactor to run.
     */
    void execute(final AbstractInteractor interactor);
}
