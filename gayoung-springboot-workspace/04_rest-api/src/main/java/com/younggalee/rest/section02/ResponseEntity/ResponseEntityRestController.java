package com.younggalee.rest.section02.ResponseEntity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.file.attribute.UserPrincipal;
import java.sql.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

//회원목록조회, 상세조회, 삭제, 수정
@RestController
@RequestMapping("/entity")
public class ResponseEntityRestController {
    private List<UserDto> users;
    public ResponseEntityRestController() {
        users = new ArrayList<>();
        users.add(new UserDto(1, "user01", "pass01", "홍길동", LocalDateTime.now()));
        users.add(new UserDto(2, "user02", "pass01", "홍길동", LocalDateTime.now()));
        users.add(new UserDto(3, "user03", "pass01", "홍길동", LocalDateTime.now()));
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers(){
        // 회원목록 조회 서비스 호출 => 회원목록 데이터 반환되어옴 (users)
        if(users.isEmpty()){
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
//            ResponseErrorMessage responseErrorMessage = ResponseErrorMessage.builder()
//                    .code("00")
//                    .message("회원조회 실패")
//                    .describe("가져온 회원정보가 없습니다.")
//                    .build();
//            return new ResponseEntity<>(responseErrorMessage, HttpStatus.NOT_FOUND);
        }

        // 응답본문(body)에 담을 데이터
        ResponseMessage responseMessage = ResponseMessage.builder()
                .status(200)
                .message("조회성공")
                .results(Map.of("user", users)) // result: {user: [{}, {}, ,,]}
                . build();

        // 응답헤더(headers)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json"));

        //return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
        return ResponseEntity.ok().headers(headers).body(responseMessage);
    }

    //  /user/1     /user/2    쿼리스트링이 아닌 자원에 대한 경로변수로 url 요청받고 응답해보기
    @GetMapping("/users/{userNo}")
    public ResponseEntity<?> findUserByNo(@PathVariable int userNo){ //body의 데이터타입이 때에 따라 다르거나 없으면 ?
        // 회원 상세 조회 서비스 호출
        UserDto foundedUser = null;

        for(UserDto user : users){
            if(user.getNo() == userNo){
                foundedUser = user;
            }
        }
        // 서비스 실행 결과 검증
        if(foundedUser == null){ // 오류코드, 오류메시지, 상세메세지
            ResponseErrorMessage responseErrorMessage = ResponseErrorMessage.builder()
                    .code("ERROR_01")
                    .message("회원 조회 실패")
                    .describe("회원정보를 찾을 수 없습니다.")
                    .build();
            return new ResponseEntity<>(responseErrorMessage, HttpStatus.NOT_FOUND);
        }

        ResponseMessage responseMessage = ResponseMessage.builder()
                .status(200)
                .message("회원 조회 성공")
                .results(Map.of("user", foundedUser)).build();

        return ResponseEntity.ok().body(responseMessage);
    }

    //자원 생성 post + 요청 파라미터 requestBody form
    @PostMapping("/users")
    public ResponseEntity<?> registUser(@RequestBody UserDto registInfo){ // id, pwd, name
        // 데이터입력값 검증(유효성검사) 해줘야함.
        if(registInfo.getId() == null || registInfo.getPassword() == null || registInfo.getName() == null){
            return ResponseEntity.badRequest().body(ResponseErrorMessage.builder()
                    .code("ERROR_02")
                    .message("필수 입력값 누락")
                    .describe("아이디,비번,이름은 반드시 입력되어야 합니다.")
                    .build());
        }
        // 회원 등록 서비스 요청
        int lastUserNo = users.get(users.size() - 1).getNo();
        registInfo.setNo(lastUserNo + 1);
        registInfo.setCreatedDt(LocalDateTime.now());
        users.add(registInfo);

        /* 자원생성 API - 성공했을 경우 : 잘생성됐고, 자원경로는 여기야 (보통 이렇게 작업함)
        1) HTTP Status Code : 201
        2) 응답 본문 담지 않음
        3) Headers의 Location헤더 : 현재 생성된 자원 URI  /entity/users/4 담아서 응답 이후 수정 삭제할 수 있으니까
         */

        return ResponseEntity
                .created(URI.create("/entity/users/" + (lastUserNo+1)))
                .build(); //이래야 responseEntity 객체로 생성됨 (출력형태맞추기)
    }

    /*
        PUT    /users/수정할회원번호
        요청 전달값 (Request Body - Json 문자열

        1) 입력값 검증 - 검증 실패 : 400, {code: 01 ~~}
        2) 회원 조회 - 조회 실패 : 404, {code: 02 ~~}
        3) 회원정보 수정 - 수정성공 : 201, Location헤더: 수정된 자원 URI
     */

    @PutMapping("/users/{userNo}")
    public ResponseEntity<?> updateInfo(@PathVariable int userNo, @RequestBody UserDto updateInfo){
        // 1) 입력값 검증
        if(updateInfo.getId() == null || updateInfo.getPassword() == null || updateInfo.getName() == null){
            return ResponseEntity.badRequest()
                                .body(ResponseErrorMessage.builder()
                                                          .code("ERROR_01")
                                                          .message("필수 입력값 누락")
                                                          .describe("비밀번호랑 이름은 반드시 입력되어야합니다.")
                                                          .build());
        }

        // 2) 회원조회
        UserDto foundedUser = null;

        for(UserDto user : users){
            if(user.getNo() == userNo){
                foundedUser = user;
            }
        }

        if(foundedUser == null){ // 오류코드, 오류메시지, 상세메세지
            ResponseErrorMessage responseErrorMessage = ResponseErrorMessage.builder()
                    .code("ERROR_02")
                    .message("회원 조회 실패")
                    .describe("회원정보를 찾을 수 없습니다.")
                    .build();
            return new ResponseEntity<>(responseErrorMessage, HttpStatus.NOT_FOUND); // notfound는 build body 못 담음 1)처럼 못함
        }

        foundedUser.setId(updateInfo.getId());
        foundedUser.setName(updateInfo.getName());
        foundedUser.setPassword(updateInfo.getPassword());

        ResponseMessage responseMessage = ResponseMessage.builder()
                .status(200)
                .message("회원 조회 성공")
                .results(Map.of("user", foundedUser)).build();

        return ResponseEntity
                .created(URI.create("/entity/users/" + userNo))
                        .build();
    }

    // DELETE  /users/1
    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<?> removeUser(@PathVariable int userNo){
        UserDto foundedUser = null;
        for(UserDto user : users){
            if(user.getNo() == userNo){
                foundedUser = user;
            }
        }
//        userService.removeUser(userNo);  가 원래 컨트롤러
        // 아래 UsernotfoundException은 서비스에서 진행될 것
        // Service에서 예외가 발생해도 controller로 전파되기 때문에 exceptionhandler 동작함.
        if(foundedUser == null){ // 오류코드, 오류메시지, 상세메세지
            ResponseErrorMessage responseErrorMessage = ResponseErrorMessage.builder()
                    .code("ERROR_02")
                    .message("회원 조회 실패")
                    .describe("회원정보를 찾을 수 없습니다.")
                    .build();
            return new ResponseEntity<>(responseErrorMessage, HttpStatus.NOT_FOUND); // notfound는 build body 못 담음 1)처럼 못함
        }

        users.remove(foundedUser);

        return ResponseEntity.noContent().build(); // 204 성공
    }

    // 계속 조회된 회원이 없을 경우 예외 출력이 반복적으로 작성되니까
    // 조회된 회원이 없을 경우 공통적으로 실행시킬 메소드
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseErrorMessage> handleUserNotFound(UserNotFoundException e){
        ResponseErrorMessage responseErrorMessage = ResponseErrorMessage.builder()
                    .code("00")
                    .message("회원조회 실패")
                    .describe(e.getMessage())
                    .build();
            return new ResponseEntity<>(responseErrorMessage, HttpStatus.NOT_FOUND);
    }

}
