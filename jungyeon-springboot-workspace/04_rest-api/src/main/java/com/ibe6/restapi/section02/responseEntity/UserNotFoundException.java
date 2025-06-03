package com.ibe6.restapi.section02.responseEntity;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message){
        super(message);
    }
}
