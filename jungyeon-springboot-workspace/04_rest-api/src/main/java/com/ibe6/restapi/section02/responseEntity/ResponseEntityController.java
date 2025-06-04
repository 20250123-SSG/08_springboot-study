package com.ibe6.restapi.section02.responseEntity;

import io.swagger.v3.oas.annotations.Hidden;
import org.apache.catalina.User;
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

@RequestMapping("/entity")
@RestController
public class ResponseEntityController {

    private List<UserDto> users;

    public ResponseEntityController() {
        users = new ArrayList<>();
        users.add(new UserDto(1, "user01", "pass01", "홍길동", LocalDateTime.now()));
        users.add(new UserDto(2, "user02", "pass02", "홍가라", LocalDateTime.now()));
        users.add(new UserDto(3, "user03", "pass03", "홍루루", LocalDateTime.now()));
    }

    @GetMapping("/users")
    public ResponseEntity<?> findAllUsers() {
        // 회원목록 서비스 호출 => 회원목록 데이터 반환되어옴 (users)

        if(users.isEmpty()){
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
            }

        // 응답본문(body)에 담을 데이터
        ResponseMessage responseMessage = ResponseMessage.builder()
                .status(200)
                .message("조회 성공")
                .result(Map.of("users", users)) // result: {users:[{},{},..]}
                .build();

        // 응답헤더(headers)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json"));

        //return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(responseMessage);
    }

    @GetMapping("/users/{userNo}")
    public ResponseEntity<?> findUserByNo(@PathVariable int userNo) {
        // 회원 상세 조회 서비스 호출
        UserDto foundedUser = null;
        for (UserDto user : users) {
            if (user.getNo() == userNo) {
                foundedUser = user;
            }
        }

        // 서비스 실행 결과 검증
        if (foundedUser == null) { // 조회결과없을 경우 => (오류코드, 오류메세지, 상세메세지)
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }
        ResponseMessage responseMessage = ResponseMessage.builder()
                .status(200)
                .message("조회 성공")
                .result(Map.of("user", foundedUser))
                .build();

        return ResponseEntity
                .ok()
                .body(responseMessage);

    }

    // 자원 생성 (post) + 요청 파라미터 (request body - json문자열)
    @PostMapping("/users")
    public ResponseEntity<?> registUser(@RequestBody UserDto registInfo) { //registInfo > id, pwd, name

        // 유효성검사 (입력값 검증)
        if (registInfo.getId() == null || registInfo.getPwd() == null || registInfo.getName() == null) {

            return ResponseEntity
                    .badRequest()
                    .body(ResponseErrorMessage.builder()
                            .code("01")
                            .message("필수 입력값 누락")
                            .describe("아이디, 비밀번호, 이름은 반드시 입력되어야 합니다.")
                            .build());

        }

        // 회원 등록 서비스 요청
        int lastUserNo = users.get(users.size() - 1).getNo(); // 현재 마지막회원번호
        registInfo.setNo(lastUserNo + 1);
        registInfo.setEnrollDate(LocalDateTime.now());
        users.add(registInfo);

        /*
            자원 생성 API - 성공했을 경우
            1) HTTP status Code : 201
            2) 응답 본문 담지 않음
            3) Headers의 Location헤더 :현재 생성된 자원 URI / entity/users/4
         */
        return ResponseEntity
                .created(URI.create("/entity/users/" + (lastUserNo + 1)))
                .build();
    }

    /*
        PUT / users / 수정할회원번호
        요청 전달값 (Request Body - JSON 문자열 {pwd:xx, name:xx})

        1) 입력값 검증 - 검증 실패 : 400, {code: "01", message: "필수값 입력 누락", describe:"xxx"}
        2) 회원 조회   - 조회 실패 : 404, {code: "00", message: "xxx", descrive:"xxxx"}
        3) 회원정보 수정 - 수정 성공 : 201, Location헤더:수정된자원URI
     */

    @PutMapping("/users/{userNo}")
    public ResponseEntity<?> modifyUser(@PathVariable int userNo, @RequestBody UserDto modifyInfo) {
        if (modifyInfo.getPwd() == null || modifyInfo.getName() == null) {
            return ResponseEntity
                    .badRequest()
                    .body(ResponseErrorMessage.builder()
                            .code("01")
                            .message("필수 입력값 누락")
                            .describe("비밀번호, 이름은 반드시 입력되어야 합니다.")
                            .build());
        }

        // 회원 조회
        UserDto foundedUser = null;
        for (UserDto user : users) {
            if (user.getNo() == userNo) {
                foundedUser = user;
            }
        }

        if (foundedUser == null) {
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }

        foundedUser.setPwd(modifyInfo.getPwd());
        foundedUser.setName(modifyInfo.getName());

        return ResponseEntity
                .created(URI.create("/entity/users" + userNo))
                .build();

    }


    // Delete / users/ 1
    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<?> removeUser(@PathVariable int userNo){

        UserDto foundedUser = null;
        for (UserDto user : users) {
            if (user.getNo() == userNo) {
                foundedUser = user;
            }
        }

        if (foundedUser == null) {
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }

        users.remove(foundedUser);

        return ResponseEntity
                .noContent()
                .build();
    }

    // 조회된 회원이 없을 경우 공통적으로 실행시킬 메소드
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseErrorMessage> handleUserNotFound(UserNotFoundException e){
        ResponseErrorMessage responseErrorMessage = ResponseErrorMessage.builder()
                .code("00")
                .message("회원 조회 실패")
                .describe(e.getMessage())
                .build();
        return new ResponseEntity<>(responseErrorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseErrorMessage> handleException(Exception e){
        ResponseErrorMessage responseErrorMessage = ResponseErrorMessage.builder()
                .code("09")
                .message("서버 내부 오류")
                .describe(e.getMessage())
                .build();
        return new ResponseEntity<>(responseErrorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}