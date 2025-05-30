package com.ino.rest.section04.swagger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

//@ControllerAdvice // AOP 기술
public class ExceptionController {
    @ExceptionHandler(UserNotFoundException.class) // Dispatcher Servlet 으로 반환
    public ResponseEntity<ResponseErrorMessage> handleUserNotFoundException(UserNotFoundException e) {
        ResponseErrorMessage responseErrorMessage = ResponseErrorMessage.builder()
                .code("00")
                .message("failed find user")
                .describe(e.getMessage())
                .build();
        return new ResponseEntity<>(responseErrorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorMessage> handleUserInvalidInputException(MethodArgumentNotValidException e) {

        String code = null;
        String message = null;
        String describe = null;

        BindingResult bindingResult = e.getBindingResult();
        switch (bindingResult.getFieldError().getCode()) {
            case "NotNull", "NotBlank":
                code = "01";
                message = "필수 입력 값 누락";
                break;
            case "Size":
                code = "02";
                message = "입력 가능 크기를 벗어남";
                break;
            default:
                code = "xx";
                message = "unknown Error";
        }
        ResponseErrorMessage responseErrorMessage = ResponseErrorMessage.builder()
                .code(code)
                .message(message)
                .describe(e.getMessage())
                .build();

        return new ResponseEntity<>(responseErrorMessage, HttpStatus.BAD_REQUEST);
    }

    //경로 변수 타입 불일치 시 발생 예외
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseErrorMessage> handleTypeMismatchException(MethodArgumentTypeMismatchException e){

        return ResponseEntity
                .badRequest()
                .body(ResponseErrorMessage.builder()
                        .code("03")
                        .message("경로 변수 오류")
                        .describe("wrong type data")
                        .build());
    }
}
