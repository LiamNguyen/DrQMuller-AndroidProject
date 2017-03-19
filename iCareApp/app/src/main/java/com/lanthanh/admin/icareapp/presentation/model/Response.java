package com.lanthanh.admin.icareapp.presentation.model;

/**
 * @author longv
 * Created on 19-Mar-17.
 */

public class Response {
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;

    private int status;
    private SuccessResponse successResponse;
    private FailResponse failResponse;

    private Response(Builder builder){
        this.status = builder.status;
        this.successResponse = builder.successResponse;
        this.failResponse = builder.failResponse;
    }

    public int getStatus(){
        return this.status;
    }

    public SuccessResponse getSuccessResponse(){
        return successResponse;
    }

    public FailResponse getFailResponse(){
        return failResponse;
    }

    public static final class Builder {
        private int status;
        private SuccessResponse successResponse;
        private FailResponse failResponse;

        public Builder(){}

        public Builder setStatus(int status){
            this.status = status;
            return this;
        }

        public Builder setSuccessResponse(SuccessResponse successResponse){
            this.successResponse = successResponse;
            return this;
        }

        public Builder setFailResponse(FailResponse failResponse){
            this.failResponse = failResponse;
            return this;
        }

        public Response build(){
            return new Response(this);
        }
    }

    public static class FailResponse {
        private String status, error, errorCode;

        public FailResponse(String status, String error, String errorCode){
            this.status = status;
            this.error = error;
            this.errorCode = errorCode;
        }

        public String getStatus(){
            return status;
        }

        public String getError(){
            return error;
        }

        public String getErrorCode(){
            return errorCode;
        }
    }

    public static class SuccessResponse<T> {
        private T successObj;

        public SuccessResponse(T successObj){
            this.successObj = successObj;
        }

        public T getSuccessObject(){
            return successObj;
        }
    }
}
