package com.podoseee.rest.section03.valid;

import com.podoseee.rest.section03.valid.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/valid")
@RestController
public class ValidationRestController {

    private List<UserDto> users;
    public ValidationRestController(){
        users = new ArrayList<>();
        users.add(new UserDto(1, "user01", "pass01", "이마크", LocalDateTime.now()));
        users.add(new UserDto(2, "user02", "pass02", "나재민", LocalDateTime.now()));
        users.add(new UserDto(3, "user03", "pass03", "이제노", LocalDateTime.now()));
        }

    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers() {

        // 서비스 ----------------------------------------------------
        List<UserDto> foundedUsers = users; // 쿼리 실행

        if(foundedUsers.isEmpty()){ // 쿼리 실행 결과 검증
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }
        // -----------------------------------------------------------

        return ResponseEntity
                .ok()
                .body(ResponseMessage.builder()
                        .status(200)
                        .message("회원 목록 조회 성공")
                        .results(Map.of("users", foundedUsers))
                        .build());

    }

    @GetMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> findUserByNo(@PathVariable int userNo){

        // 서비스 -------------------------------------------------------------
        UserDto foundedUser = null;
        for(UserDto user : users){
            if(user.getNo() == userNo){
                foundedUser = user;
            }
        }

        if(foundedUser == null){
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }
        // --------------------------------------------------------------------

        return ResponseEntity
                .ok()
                .body(ResponseMessage.builder()
                        .status(200)
                        .message("회원 상세 조회 성공")
                        .results(Map.of("user", foundedUser))
                        .build());
    }

    @PutMapping("/users/{userNo}")
    public ResponseEntity<?> modifyUser(@PathVariable int userNo
                                      , @Validated (ModifyGroup.class) @RequestBody UserDto modifyInfo){

        // 서비스 -----------------------------------------------------------------
        UserDto foundedUser = null;
        for(UserDto user : users){
            if(user.getNo() == userNo){
                foundedUser = user;
            }
        }

        if(foundedUser == null){
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }

        // 기존 유저 삭제
        users.remove(foundedUser);

        // 새 정보로 추가
        modifyInfo.setNo(userNo); // 번호는 URL PathVariable 기준으로 고정
        modifyInfo.setEnrollDate(foundedUser.getEnrollDate()); // 등록일은 원래 날짜 유지

        users.add(modifyInfo);
        // ------------------------------------------------------------------------

        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<?> removeUser(@PathVariable int userNo){

        // 서비스 -----------------------------------------------------------------
        UserDto foundedUser = null;
        for(UserDto user : users){
            if(user.getNo() == userNo){
                foundedUser = user;
            }
        }

        if(foundedUser == null){
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }

        users.remove(foundedUser);
        // ------------------------------------------------------------------------

        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseMessage> registUser(@Validated @RequestBody UserDto registInfo){

        // 자동 번호 부여
        int lastUserNo = users.get(users.size() - 1).getNo();
        registInfo.setNo(lastUserNo + 1);
        registInfo.setEnrollDate(LocalDateTime.now());

        // 사용자 추가
        users.add(registInfo);

        // 생성된 사용자 정보 응답 포함
        return ResponseEntity
                .created(URI.create("/valid/users/" + registInfo.getNo()))
                .body(ResponseMessage.builder()
                        .status(201)
                        .message("회원 등록 성공")
                        .results(Map.of("user", registInfo))
                        .build());
    }
}
