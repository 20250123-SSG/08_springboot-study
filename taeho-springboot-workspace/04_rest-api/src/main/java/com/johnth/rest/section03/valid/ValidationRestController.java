package com.johnth.rest.section03.valid;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Hidden

@RequestMapping("/valid")
@RestController
public class ValidationRestController {

    private List<UserDto> users;
    public ValidationRestController(){
        users = new ArrayList<>();
        users.add(new UserDto(1, "user01", "pass01", "홍길동", LocalDateTime.now()));
        users.add(new UserDto(2, "user02", "pass02", "김말똥", LocalDateTime.now()));
        users.add(new UserDto(3, "user03", "pass03", "강개순", LocalDateTime.now()));
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers(){

        // 서비스 -------------------------------------------------
        List<UserDto> foundedUsers = users; // 쿼리 실행

        if(foundedUsers.isEmpty()){ // 쿼리 실행 결과 검증
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }
        // --------------------------------------------------------

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

        // 서비스 ----------------------------------------------------------
        UserDto foundedUser = null;
        for(UserDto user : users){
            if(user.getNo() == userNo){
                foundedUser = user;
            }
        }

        if(foundedUser == null){
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }
        // ------------------------------------------------------------------

        return ResponseEntity
                .ok()
                .body(ResponseMessage.builder()
                        .status(200)
                        .message("회원 상세 조회 성공")
                        .results(Map.of("user", foundedUser))
                        .build());
    }

    @PostMapping("/users")
    public ResponseEntity<?> registUser(@Validated(RegistGroup.class) @RequestBody UserDto registInfo){

        // 서비스 -------------------------------------------------------
        int lastUserNo = users.get(users.size()-1).getNo(); // 마지막 회원번호
        registInfo.setNo(lastUserNo + 1); // 새로 생성될 자원의 식별번호
        registInfo.setEnrollDate(LocalDateTime.now());
        users.add(registInfo);
        // --------------------------------------------------------------

        return ResponseEntity
                .created(URI.create("/valid/users/" + (lastUserNo+1)))
                .build();

    }

    @PutMapping("/users/{userNo}")
    public ResponseEntity<?> modifyUser(@PathVariable int userNo
                                      , @Validated(ModifyGroup.class) @RequestBody UserDto modifyInfo){

        // 서비스 --------------------------------------------------------------
        UserDto foundedUser = null;
        for(UserDto user : users){
            if(user.getNo() == userNo){
                foundedUser = user;
            }
        }

        if(foundedUser == null){
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }

        foundedUser.setPwd(modifyInfo.getPwd());
        foundedUser.setName(modifyInfo.getName());
        // ---------------------------------------------------------------------

        return ResponseEntity
                .created(URI.create("/valid/users/" + userNo))
                .build();

    }

    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<?> removeUser(@PathVariable int userNo){

        // 서비스 -------------------------------------------------
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
        // --------------------------------------------------------

        return ResponseEntity
                .noContent()
                .build();
    }


}
