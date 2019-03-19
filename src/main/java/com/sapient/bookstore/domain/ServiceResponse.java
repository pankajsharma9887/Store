package com.sapient.bookstore.domain;
/**
 * Model
 * @author Pankaj Sharma
 * @since 1.0
 */
public class ServiceResponse<T> {
    private boolean hasError;
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    public ServiceResponse(){}
    public ServiceResponse(Boolean hasError, T data,String message)
    {
        this.hasError=hasError;
        this.data=data;
        this.message=message;
    }
    public ServiceResponse(Boolean hasError,String message)
    {
        this.hasError=hasError;
        this.data=data;
        this.message=message;
    }
    public ServiceResponse(Boolean hasError,T data)
    {
        this.hasError=hasError;
        this.data=data;
        this.message=message;
    }
    public Boolean hasError() {
        return hasError;
    }

    public void setHasError(Boolean hasError) {
        this.hasError = hasError;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
