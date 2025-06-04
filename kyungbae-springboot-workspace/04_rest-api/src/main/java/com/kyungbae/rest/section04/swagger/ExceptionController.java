package com.kyungbae.rest.section04.swagger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseErrorMessage> handlerUserNotFoundException(UserNotFoundException e ){
        ResponseErrorMessage errorMessage = ResponseErrorMessage.builder()
                .code("00")
                .message("회원 조회 실패")
                .describe(e.getMessage())
                .build();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorMessage> handleUserInvalidInputException(MethodArgumentNotValidException e){
        String code = null;
        String message = null;
        String describe = null;

        BindingResult bindingResult = e.getBindingResult();
        switch(bindingResult.getFieldError().getCode()) {
            case "NotNull", "NotBlank" :
                code = "01";
                message = "필수 입력 값 누락 또는 공백 입력";
                break;
            case "Size" :
                code = "02";
                message = "입력 가능한 크기를 벗어났습니다.";
                break;
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

    // 경로 변수 타입 불일치 예외
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
