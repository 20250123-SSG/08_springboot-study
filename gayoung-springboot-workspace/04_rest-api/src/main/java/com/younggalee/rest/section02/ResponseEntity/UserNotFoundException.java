package com.younggalee.rest.section02.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;


public class UserNotFoundException extends RuntimeException{ // > Exception > throwable
    public UserNotFoundException(String message) {
        super(message); //  부모의 부모까지 default로 전달해줘야함.
    }
}
