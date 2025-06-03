package com.kyungbae.rest.section02.responseEntity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/entity")
@RestController
public class ResponseEntityRestController {

    private List<UserDto> users;
    public ResponseEntityRestController(){
        users = new ArrayList<>();
        users.add(new UserDto(1, "user01", "pass01", "홍길동", LocalDateTime.now()));
        users.add(new UserDto(2, "user02", "pass02", "청길은", LocalDateTime.now()));
        users.add(new UserDto(3, "user03", "pass03", "백길금", LocalDateTime.now()));
    }

    @GetMapping("/users")
    public ResponseEntity<?> findAllUsers(){
        // 회원목록 조회 서비스 호출 => 회원목록 데이터 반환되어옴 (users)

        if (users.isEmpty()) {
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .body(ResponseErrorMessage
//                            .builder()
//                            .code("ERROR_000")
//                            .describe("없는 회원번호 입니다.")
//                            .build());
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }

        // 응답본문(body)에 담을 데이터
        ResponseMessage responseMessage = ResponseMessage.builder()
                .status(200)
                .message("조회성공")
                .results(Map.of("users", users))
                .build();

        // 응답헤더 설정
        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json"));
//        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);

//        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
        return ResponseEntity // builder 패턴 사용
                .ok()
                .headers(headers)
                .body(responseMessage);
    }

    @GetMapping("/users/{userNo}") // 경로변수의 값 대입
    public ResponseEntity<?> findUserByNo(@PathVariable int userNo){ // 경로변수
        // 회원 상세 조회 서비스 호출
        UserDto foundedUser = null;
        for (UserDto user : users) {
            if (user.getNo() == userNo) {
                foundedUser = user;
            }
        }
        // 서비스 실행 결과 검증
        if (foundedUser == null) { // 조회결과 없을 경우 => (오류코드, 오류메세지, 상세메세지)
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }
        ResponseMessage responseMessage = ResponseMessage
                .builder()
                .status(200)
                .message("정상")
                .results(Map.of("user", foundedUser))
                .build();
        return ResponseEntity
                .ok()
                .body(responseMessage);
    }

    // 자원 생성 (post) + 요청 파라미터 (request body - json 문자열)
    @PostMapping("/users")
    public ResponseEntity<?> registUser(@RequestBody UserDto registInfo){ // registInfo > id,pwd,name
        // 유효성검사(입력값 검증)
        if (registInfo.getId().isEmpty() || registInfo.getPwd().isEmpty() || registInfo.getName().isEmpty() || registInfo.getId().isBlank() || registInfo.getPwd().isBlank() || registInfo.getName().isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body(ResponseErrorMessage
                            .builder()
                            .code("ERROR_001")
                            .describe("아이디, 비밀번호, 이름은 반드시 입력하셔야 합니다.")
                            .build());
        }
        // 회원등록 서비스 요청
        int lastUserNo = users.get(users.size()-1).getNo(); // 마지막 회원번호
        registInfo.setNo(lastUserNo + 1);
        registInfo.setEnrollDate(LocalDateTime.now());
        users.add(registInfo);

        /*
            자원 생성 API - 성공했을 경우 (통상적으로)
            1) HTTP Status Code : 201
            2) 응답 본문 담지 않음
            3) Headers의 Location 해더 : 현재 생성된 자원 URI /entity/users/{newUserNo}
         */
        return ResponseEntity
                .created(URI.create("/entity/users/" + (lastUserNo+1)))
                .build();
    }
    /*
        PUT /users/수정할회원번호
        요청 전달값 (Request Body - JSON 문자열)

        1) 입력값 검증 - 검증 실패 : 400, {code:"ERROR_CODE_01", message:"필수값 입력 누락", describe:"xxx"}
        2) 회원 조회   - 조회 실패 : 404, {code:"00", message:"xxx", describe:"xxxx"}
        3) 회원정보 수정 - 수정 성공 : 201, Location해더: 수정된자원의URI
     */

    @PutMapping("/users/{userNo}")
    public ResponseEntity<?> updateUser(@PathVariable int userNo,
                                        @RequestBody UserDto user){
        // 입력값 검증
        if (user.getId().isEmpty() ||
                user.getPwd().isEmpty() ||
                user.getId().isBlank() ||
                user.getPwd().isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body(ResponseErrorMessage
                            .builder()
                            .code("ERROR_001")
                            .describe("아이디, 비밀번호, 이름은 반드시 입력하셔야 합니다.")
                            .build());
        }
        UserDto foundUser = null;
        for (UserDto u : users) {
            if (u.getNo() == userNo) {
                foundUser = user;
            }
        }
        if (foundUser == null) {
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .body(ResponseErrorMessage
//                            .builder()
//                            .code("ERROR_000")
//                            .describe("없는 회원번호 입니다.")
//                            .build());
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }
        return ResponseEntity
                .created(URI.create("/entity/users/"+userNo))
                .build();
    }

    // DELETE /users/1
    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<?> removeUser(@PathVariable int userNo){

        UserDto foundUser = null;
        for (UserDto u : users) {
            if (u.getNo() == userNo) {
                foundUser = u;
            }
        }
        if (foundUser == null) {
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .body(ResponseErrorMessage
//                            .builder()
//                            .code("ERROR_000")
//                            .describe("없는 회원번호 입니다.")
//                            .build());
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }
        users.remove(foundUser);

        return ResponseEntity.noContent().build();
    }

    // 조회된 회원이 없을 경우 공통적으로 실행시킬 메소드 (catch 블럭)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseErrorMessage> handleUserNotFound(UserNotFoundException e){
        ResponseErrorMessage errorMessage = ResponseErrorMessage
                .builder()
                .code("ERROR_000")
                .message("조회 결과 없음")
                .describe(e.getMessage())
                .build();
         return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseErrorMessage> handleException(Exception e){
        ResponseErrorMessage responseErrorMessage = ResponseErrorMessage.builder()
                .code("ERROR_009")
                .message("서버 오류")
                .describe(e.getMessage())
                .build();
        return ResponseEntity.internalServerError().body(responseErrorMessage);
    }


}
