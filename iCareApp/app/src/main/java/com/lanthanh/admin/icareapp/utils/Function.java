package com.lanthanh.admin.icareapp.utils;

/**
 * @author longv
 *         Created on 24-Mar-17.
 */

public interface Function {
    interface VoidParam<T> {
        void apply(T t);
    }

    interface VoidEmpty {
        void apply();
    }
}
