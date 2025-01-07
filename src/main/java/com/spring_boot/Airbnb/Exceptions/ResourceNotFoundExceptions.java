package com.spring_boot.Airbnb.Exceptions;

public class ResourceNotFoundExceptions extends RuntimeException {

    public ResourceNotFoundExceptions(String message){
        super(message);
    }


}
