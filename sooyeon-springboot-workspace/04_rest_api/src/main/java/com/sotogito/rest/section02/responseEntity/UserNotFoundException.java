package com.sotogito.rest.section02.responseEntity;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

    /***
     * ### 예외 처리 순서
     * 1. DispatcherServlet
     * 2. Controller 핸들러 메서드
     * 3. @ExceptionHandler 정의 예외 메서드 실행
     * 4. DispatcherServlet
     */


}
