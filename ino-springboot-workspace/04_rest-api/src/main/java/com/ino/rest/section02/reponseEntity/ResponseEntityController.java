package com.ino.rest.section02.reponseEntity;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Hidden
@RestController
@RequestMapping("/entity")
public class ResponseEntityController {
    private List<UserDto> list;

    public ResponseEntityController() {
        list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            UserDto user = new UserDto(
                    i, // no
                    "user" + i, // id
                    "pass" + i, // pwd
                    "사용자" + i, // name
                    LocalDateTime.now().minusDays(10 - i) // 현재 날짜로부터 역순으로 등록일자 설정
            );
            list.add(user);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> findAllUsers() {

        if(list.isEmpty()){
            throw new RuntimeException("User Not Found");
        }

        ResponseMessage responseMessage = ResponseMessage.builder()
                .status(200)
                .message("find success")
                .results(Map.of("list", list))
                .build();
        HttpHeaders headers = new HttpHeaders();
        // headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        // headers.setContentType(new MediaType("application", "json"));
        headers.setContentType(new MediaType("application", "json"));
        return new ResponseEntity(responseMessage, headers, HttpStatus.OK);
    }

    @GetMapping("/users/{userNo}")
    public ResponseEntity<?> findUserByNo(@PathVariable int userNo) {
        UserDto foundUser = null;
        for (UserDto user : list) {
            if (user.getNo() == userNo) {
                foundUser = user;
            }
        }

        // 서비스 실행 결과 검증
        if (foundUser == null) { // 에러코드, 에러메세지, 상세메세지 반환
            throw new UserNotFoundException("cannot find user");
        } else {
            ResponseMessage responseMessage = ResponseMessage.builder()
                    .status(200)
                    .message("SUCCESS")
                    .results(Map.of("user", foundUser))
                    .build();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return ResponseEntity
                    .ok()
                    .body(responseMessage);
        }
    }

    // 자원 생성 (post + req param (request body - jsonString)
    @PostMapping("/users")
    public ResponseEntity<?> registUser(@RequestBody UserDto user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 유효성 검사
        if (user.getId() == null || user.getPwd() == null || user.getName() == null) {
            return ResponseEntity
                    .badRequest()
                    .headers(headers)
                    .body(
                            ResponseErrorMessage.builder()
                                    .code("400")
                                    .message("wrong format")
                                    .describe("user data wrong")
                                    .build()
                    );
        } else {
            int lastUserNo = list.get(list.size() - 1).getNo(); // last userNo
            user.setNo(lastUserNo + 1);
            user.setEnrollDate(LocalDateTime.now());
            list.add(user);
            return ResponseEntity
                    .created(URI.create("entity/users/" + (lastUserNo + 1)))
                    .build();
        }
    }

    @PutMapping("/users/{userNo}")
    public ResponseEntity<?> updateUser(@PathVariable int userNo, @RequestBody UserDto user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (user.getId() == null || user.getPwd() == null || user.getName() == null) {
            return ResponseEntity
                    .badRequest()
                    .headers(headers)
                    .body(
                            ResponseErrorMessage.builder()
                                    .code("404")
                                    .message("wrong format")
                                    .describe("user data wrong")
                                    .build()
                    );
        } else if (list.get(userNo) == null) {
            ResponseErrorMessage respError = new ResponseErrorMessage().builder()
                    .code("00")
                    .message("failed get user")
                    .describe("cannot find user")
                    .build();
            return new ResponseEntity<>(respError, HttpStatus.NOT_FOUND);
        } else {
            list.set(userNo, user);
            return ResponseEntity
                    .created(URI.create("entity/users/" + (userNo)))
                    .build();
        }
    }

    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<?> deleteUser(@PathVariable int userNo){
        // service layer communication - 조회 , 삭제

        // return value
        return ResponseEntity
                .noContent()// 204
                .build();
    }

    // 조회된 회원이 없을 경우 공통 실행 메소드
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseErrorMessage> handleUserNotFound(UserNotFoundException e){
        e.printStackTrace();
        ResponseErrorMessage responseErrorMessage = ResponseErrorMessage.builder()
                .code("404")
                .message("User Not found")
                .describe(e.getMessage())
                .build();
        return new ResponseEntity<>(responseErrorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseErrorMessage> handleException(Exception e){
        e.printStackTrace();
        ResponseErrorMessage responseErrorMessage = ResponseErrorMessage.builder()
                .code("09")
                .message("Internal Server Error")
                .describe(e.getMessage())
                .build();
        return new ResponseEntity<>(responseErrorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

