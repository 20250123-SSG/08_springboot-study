package com.jjanggu.rest.section04.swagger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


/*
    ## @ControllerAdvice ##
    1. 애플리케이션 내의  컨트롤러에서 발생되는 예외를 처리할 수 있음
    2. Advice 이므로 AOP 기술이 적용된 어노테이션
    3. 컨트롤러에 포횜도외어야 될 예외
     */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseErrorMessage> handleUserNotFoundException(UserNotFoundException e){
        ResponseErrorMessage responseErrorMessage = ResponseErrorMessage.builder()
                .code("00")
                .message("회원 조회 실패")
                .describe(e.getMessage())
                .build();
        return new ResponseEntity<>(responseErrorMessage, HttpStatus.NOT_FOUND); // 404
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // DTO에 설정한 유효성 검사를 통과하지 못할 경우 발생
    public ResponseEntity<ResponseErrorMessage> handleUserInvalidInputException(MethodArgumentNotValidException e){

        String code = null;
        String message = null;
        String describe = null;

        BindingResult bindingResult = e.getBindingResult();
        switch(bindingResult.getFieldError().getCode()){
            case "NotNull", "NotBlank":
                code="01";
                message="필수 입력 값 누락 또는 공백 입력";
                break;
            case "Size":
                code="02";
                message = "입력 가능한 크기를 벗어난 값 읿력";
        }

        describe = bindingResult.getFieldError().getDefaultMessage();

        return ResponseEntity
                .badRequest()
                .body(ResponseErrorMessage.builder()
                        .code(code)
                        .message(message)
                        .describe(describe)
                        .build());
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class) // 경로 변수의 타입 불일치 시 발생되는 예외
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
