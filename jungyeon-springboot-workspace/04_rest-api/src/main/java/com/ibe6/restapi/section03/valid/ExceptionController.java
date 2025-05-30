package com.ibe6.restapi.section03.valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/*
    ## @ControllerAdvice ##
    1. 애플리케이션 내의 모든 컨트롤러에서 발생되는 예외를 처리할 수 있음
    2. Advice 이므로 AOP 기술이 적용된 어노테이션
    3. 컨트롤러에 포함되어야 될 예외 처리 코드를 따로 분리해서 관리할 수 있음
       코드의 중복 문제를 해결하고, 서로의 관심사를 분리할 수 있음
 */

//@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseErrorMessage> handleUserNotFoundException(UserNotFoundException e) {
        ResponseErrorMessage responseErrorMessage = ResponseErrorMessage.builder()
                .code("00")
                .message("회원 조회 실패")
                .describe(e.getMessage())
                .build();
        return new ResponseEntity<>(responseErrorMessage, HttpStatus.NOT_FOUND); // 404
    }

    // 경로 변수의 타입 불일치
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseErrorMessage> handlePathVariableException(MethodArgumentTypeMismatchException e){
        return ResponseEntity
                .badRequest()
                .body(ResponseErrorMessage.builder()
                        .code("03")
                        .message("경로 변수 오류")
                        .describe("잘못된 타입의 데이터가 입력되었습니다.")
                        .build());
    }

}
