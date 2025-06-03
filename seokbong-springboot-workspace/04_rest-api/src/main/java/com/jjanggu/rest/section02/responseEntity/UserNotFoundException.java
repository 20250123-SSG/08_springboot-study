package com.jjanggu.rest.section02.responseEntity;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message){
        super(message);
    }
}
