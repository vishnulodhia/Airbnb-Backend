package com.spring_boot.Airbnb.Exceptions;

public class UnAuthorisedException extends RuntimeException{
    public UnAuthorisedException(String message){
        super(message);
    }

}
