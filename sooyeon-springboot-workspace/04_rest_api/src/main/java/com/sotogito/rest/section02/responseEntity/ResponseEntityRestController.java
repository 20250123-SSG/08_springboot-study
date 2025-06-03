package com.sotogito.rest.section02.responseEntity;

import org.apache.coyote.ErrorState;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.type.ErrorType;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

@RequestMapping("/entity")
@RestController
public class ResponseEntityRestController {

    private List<UserDto> users;

    public ResponseEntityRestController() {
        users = new ArrayList<UserDto>();
        users.add(new UserDto(1, "user01", "pass01", "홍", LocalDateTime.now()));
        users.add(new UserDto(2, "user02", "pass02", "길", LocalDateTime.now()));
        users.add(new UserDto(3, "user03", "pass03", "동", LocalDateTime.now()));
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        if(users.isEmpty()) {
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }
        /// 회원목록 조회 서비스 호출 -> 회원목록 데이터 반환되어옴(users)

        /// 응답본문 body에 담을 데이터
        ResponseMessage responseMessage = ResponseMessage.builder()
                .status(HttpStatus.OK.value()) //ResponseEntity로 status 보낼 수 있는데 왜 또 보내지
                .message("조회 성공")
                .result(Map.of("users", users))
                .build();

        /// 응답해더 header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); //public static final MediaType APPLICATION_JSON = new MediaType("application", "json");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(responseMessage);
    }

    /// user/1, user/2
    @GetMapping("/user/{userNo}")
    public ResponseEntity<?> findUserByNo(@PathVariable Integer userNo) { //null을 허용하여 int보다 안전?
        Optional<UserDto> foundedUser = users.stream()
                .filter(user -> user.getNo() == userNo)
                .findAny();

        if (foundedUser.isEmpty()) {
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }
        ResponseMessage responseMessage = ResponseMessage.builder()
                .status(HttpStatus.OK.value()) /// 개발자가 정의한 더 자세한 200결과 ex)조회 성공 : 200-3
                .message("조회 성공")
                .result(Map.of("user", foundedUser.get()))
                .build();

        return ResponseEntity.ok().body(responseMessage);
    }

    // 자원 생성(post) + 요청 파라미터(request body - json 문자열)
    @PostMapping("/users")
    public ResponseEntity<?> registUser(@RequestBody UserDto registInfo) { //유효성 검사는 프론트+백 둘 다
        //요효성검사(입력값 검증)
        if (registInfo.getId() == null || registInfo.getPwd() == null || registInfo.getName() == null) {
            return ResponseEntity
                    .badRequest()
                    .body(ResponseErrorMessage.builder()
                            .code("01")
                            .message("필수 입력값 누락")
                            .description("아이디, 비밀번호, 이름은 반드시 입력되어야합니다.")
                            .build()
                    );
        }

        int lastUserNo = (users.get(users.size() - 1)).getNo();
        registInfo.setNo(lastUserNo + 1);
        registInfo.setEnrollDate(LocalDateTime.now());
        users.add(registInfo);
        /**
         * 자원 생성 API - 성공했을 경우
         * 1) HTTP Status Code :201 = CREATED
         * 2) 응답 본문 담지 않음
         * 3) Headers의 Location해더 : 현재 생성된 자원 URI를 담음 /entity/users/4
         */
        return ResponseEntity
                .created(URI.create("/entity/users/" + registInfo.getNo())) //클라이언트에게 생성된 user정보를 넘김
                .build();
    }

    /**
     * PUT /users/수정할회원번호
     * 요청 전달값 (Request Body - JSON 문자열)
     *
     * 1) 입력값 검증 : 검증 실패 : 400 (BAD_REQUEST) + body
     * 2) 회원 조회 : 조회 실패 : 404 (NO_FOUND) + body
     * 3) 회원정보 수정 : 수정 성공 : 201 (NO_CONTENT) + Location해더(수정된자원 URL)
     */

    @PutMapping("/users/{userNo}")
    public ResponseEntity<?> modifyUser(@PathVariable Integer userNo,
                                        @RequestBody UserDto modifyUserInfo) {

        if (modifyUserInfo.getId() == null || modifyUserInfo.getPwd() == null || modifyUserInfo.getName() == null) {
            return ResponseEntity
                    .badRequest()
                    .body(ResponseErrorMessage.builder()
                            .code("01")
                            .message("필수 입력값 누락")
                            .description("아이디, 비밀번호, 이름은 반드시 입력되어야합니다.")
                            .build()
                    );
        }

        Optional<UserDto> foundedUser = users.stream()
                .filter(user -> user.getNo() == userNo)
                .findAny();

        if(foundedUser.isEmpty()) {
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }

        UserDto user = foundedUser.get();
        user.setId(modifyUserInfo.getId());
        user.setPwd(modifyUserInfo.getPwd());
        user.setName(modifyUserInfo.getName());

        return ResponseEntity
                .created(URI.create("/entity/user/" + userNo))
                .build();
//        return ResponseEntity.noContent().build(); /// 수정은 어차피 사용자의 id를 알고있기 때문에 url을 보내줄 필요가 있나?
    }


    /// DELETE /users/1
    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userNo) {
        /// ////////////////////////////////사 실 서 비 스 코 드 ///////////////////////////////
        Optional<UserDto> foundedUser = users.stream()
                .filter(user -> user.getNo() == userNo)
                .findAny();

        if(foundedUser.isEmpty()) {
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.-delete");
        }
        users.remove(foundedUser.get());
        /// ////////////////////////////////////////////////////////////////////////////

        return ResponseEntity.noContent().build(); ///삭제된 user이기 때문에 데이터를 넘길필요가 없음
    }


    ///조회된 회원이 없을 경우 공통적으로 실행할 메서드 -> 여기정의하나?
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseErrorMessage> handleUserNotFound(UserNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ResponseErrorMessage.builder()
                        .code("00")
                        .message("회원 조회 실패")
                        .description(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseErrorMessage> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseErrorMessage.builder()
                        .code("09")
                        .message("서버 내부 오류")
                        .description(e.getMessage())
                        .build()
                );
    }


}
