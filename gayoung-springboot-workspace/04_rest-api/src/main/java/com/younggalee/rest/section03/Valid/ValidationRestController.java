package com.younggalee.rest.section03.Valid;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RequestMapping("/valid")
@RestController
public class ValidationRestController {
    private List<UserDto> users;

    public ValidationRestController() {
        users = new ArrayList<>();
        users.add(new UserDto(1, "user01", "pass01", "홍길동", LocalDateTime.now()));
        users.add(new UserDto(2, "user02", "pass01", "홍길동", LocalDateTime.now()));
        users.add(new UserDto(3, "user03", "pass01", "홍길동", LocalDateTime.now()));
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers() {

        // 서비스 -------------------
        List<UserDto> foundedUsers = users; // 쿼리 실행

        if (foundedUsers.isEmpty()) { // 쿼리 실행 결과 검증
            throw new UserNotFoundException("회원정보를 찾을 수 없습니다.");
        }
        // ------

        return ResponseEntity
                .ok()
                .body(ResponseMessage.builder()
                        .status(200)
                        .message("회원 조회 성공")
                        .results(Map.of("users", foundedUsers)).build());

    }

    @GetMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> findUserByNo(@PathVariable int userNo) {
        // 서비스 --------
        UserDto foundedUser = null;
        for (UserDto user : users) {
            if (user.getNo() == userNo) {
                foundedUser = user;
            }
        }
        if (foundedUser == null) {
            throw new UserNotFoundException("회원정보를 찾을 수 없습니다.");
        }
        // --------
        return ResponseEntity
                .ok()
                .body(ResponseMessage.builder()
                        .status(200)
                        .message("회원상세조회성공")
                        .results(Map.of("user", foundedUser))
                        .build());

    }

    //@Validated : 입력값이 userdto에 매핑되어 각 필드에 담길때 유효성검사를 해주겠다는 의미
    @PostMapping("/users")
    public ResponseEntity<ResponseMessage> registUser(@Validated @RequestBody UserDto registInfo) {
        // 서비스 -----
        int lastUserNo = users.get(users.size() - 1).getNo(); // 마지막 회원번호
        registInfo.setNo(lastUserNo + 1); // 새로 생성될 자원의 식별번호
        registInfo.setCreatedDt(LocalDateTime.now());
        users.add(registInfo);
        //------------

        return ResponseEntity
                .created(URI.create("/valid/users/" + (lastUserNo + 1)))
                .build();
    }

    @PutMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> updateUser(@PathVariable int userNo
                                                    , @Validated @RequestBody UserDto updateInfo) {
        // 서비스 ----------------
        UserDto foundedUser = null;
        for(UserDto user : users) {
            if (user.getNo() == userNo) {
                foundedUser = user;
            }
        }
        if (foundedUser == null) {
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }

        foundedUser.setPassword(updateInfo.getPassword());
        foundedUser.setName(updateInfo.getName());

        // -----------

        return ResponseEntity.created(URI.create("/valid/users/" + userNo)).build();
    }

    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> deleteUser(@PathVariable int userNo
            , @Validated @RequestBody UserDto updateInfo) {
        // 서비스 ----------------
        UserDto foundedUser = null;
        for(UserDto user : users) {
            if (user.getNo() == userNo) {
                foundedUser = user;
            }
        }
        if (foundedUser == null) {
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }

        users.remove(foundedUser);

        // -----------

        return ResponseEntity.noContent().build();
    }

//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<ResponseErrorMessage> handleUserNotFound(UserNotFoundException e) {
//        ResponseErrorMessage responseErrorMessage = ResponseErrorMessage.builder()
//                .code("00")
//                .message("회원조회 실패")
//                .describe(e.getMessage())
//                .build();
//        return new ResponseEntity<>(responseErrorMessage, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)  // dto에 설정한 유효성 검사를 통과하지 못한 경우
//    public ResponseEntity<ResponseErrorMessage> handleValidationException(MethodArgumentNotValidException e) {
//        String code = null;
//        String message = null;
//        String describe = null;
//
//        BindingResult bindingResult = e.getBindingResult();
//        switch (bindingResult.getFieldError().getCode()) {
//            case "NotNull", "NotBlank":
//                code = "01";
//                message = "필수 입력 값 누락 또는 공백 입력";
//                break;
//            case "Size":
//                code = "02";
//                message = "입력 가능한 크기를 벗어난 값 입력";
//                break;
//        }
//
//        describe = bindingResult.getFieldError().getDefaultMessage();
//
//        return ResponseEntity
//                .badRequest()
//                .body(ResponseErrorMessage.builder()
//                        .code(code)
//                        .message(message)
//                        .describe(describe)
//                        .build());
//    }
//
//    @ExceptionHandler(MethodArgumentTypeMismatchException.class) // 경로 변수의 타입 불일치시 발생되는 예외
//    public ResponseEntity<ResponseErrorMessage> handlePathVariableException(MethodArgumentTypeMismatchException e) {
//
//        return ResponseEntity
//                .badRequest()
//                .body(ResponseErrorMessage.builder()
//                                .code("03")
//                                .message("경로 변수 오류")
//                                .describe(e.getMessage())
//                                .build());
//    }


}
