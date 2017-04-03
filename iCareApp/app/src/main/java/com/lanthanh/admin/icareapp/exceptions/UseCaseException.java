package com.lanthanh.admin.icareapp.exceptions;

import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;

/**
 * @author longv
 *         Created on 03-Apr-17.
 */

public class UseCaseException extends Exception {
    private RepositorySimpleStatus status;

    public UseCaseException(String message) {
        super(message);
    }

    public UseCaseException(RepositorySimpleStatus status) {
        this.status = status;
    }

    public RepositorySimpleStatus getStatus() {
        return this.status;
    }
}
