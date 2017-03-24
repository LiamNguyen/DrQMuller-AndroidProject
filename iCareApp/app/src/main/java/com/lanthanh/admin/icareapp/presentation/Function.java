package com.lanthanh.admin.icareapp.presentation;

/**
 * @author longv
 *         Created on 24-Mar-17.
 */

public interface Function {
    interface Void<T> {
        void apply(T t);
    }

    interface VoidParam {
        void apply();
    }
}
