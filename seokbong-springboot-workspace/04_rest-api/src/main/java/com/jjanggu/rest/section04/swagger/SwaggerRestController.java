package com.jjanggu.rest.section04.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Tag(name = "API 목록", description = "회원 관리 API")

@RequestMapping("/swagge")
@RestController
public class SwaggerRestController {

    private List<UserDto> users;
    public SwaggerRestController() {
        users = new ArrayList<>();
        users.add(new UserDto(1, "user01", "pass01", "홍길동", LocalDateTime.now()));
        users.add(new UserDto(2, "user02", "pass02", "김말똥", LocalDateTime.now()));
        users.add(new UserDto(3, "user03", "pass03", "강개순", LocalDateTime.now()));
    }

    @Operation(summary = "회원 목록 조회", description = "회원 목록을 조회하는 기능입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
            @ApiResponse(responseCode = "00", description = "조회 실패", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))
    })
    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers(){

        // 서비스 --------------------------------------------------------------------------
        List<UserDto> foundedUsers = users; // 쿼리 실행

        if(foundedUsers.isEmpty()){ // 쿼리 실행 결과 검증
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }
        // ---------------------------------------------------------------------------------

        return ResponseEntity
                .ok()
                .body(ResponseMessage.builder()
                        .status(200)
                        .message("회원 목록 조회 성공")
                        .results(Map.of("users", foundedUsers))
                        .build());
    }

    @Operation(summary = "회원 상세 조회", description = "회원 번호에 따른 회원 상세 정보를 조회하는 기능입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
            @ApiResponse(responseCode = "00", description = "조회 실패", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class))),
            @ApiResponse(responseCode = "03", description = "경로 변수 오류", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))
    })
    @Parameter(name = "userNo", required = true, description = "조회할 회원 번호")
    @GetMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> findUserByNo(@PathVariable int userNo){

        // 서비스 ------------------------------------------------------------
        UserDto foundedUser = null;
        for(UserDto user : users){
            if(user.getNo() == userNo){
                foundedUser = user;
            }
        }

        if(foundedUser == null){
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }
        // -------------------------------------------------------------------

        return ResponseEntity
                .ok()
                .body(ResponseMessage.builder()
                        .status(200)
                        .message("회원 상세 조회 성공")
                        .results(Map.of("user", foundedUser))
                        .build());
    }

    @PostMapping("/users")
    public ResponseEntity<?> registUser(@Validated @RequestBody UserDto registInfo){

        // 서비스 ---------------------------------------------------
        int lastUserNo = users.get(users.size()-1).getNo(); // 마지막 회원번호
        registInfo.setNo((lastUserNo + 1)); // 새로 생성될 자원의 식별번호
        registInfo.setEnrollDate(LocalDateTime.now());
        users.add(registInfo);
        // ----------------------------------------------------------

        return ResponseEntity
                .created(URI.create("/valid/users/" + (lastUserNo+1)))
                .build();
    }

    @PutMapping("/users/{userNo}")
    public ResponseEntity<?> modifyUser(@PathVariable int userNo, @Validated @RequestBody UserDto modifyInfo){

        // 서비스-------------------------------------------------------
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
        // ------------------------------------------------------------

        return ResponseEntity
                .created(URI.create("/valid/users/" + userNo))
                .build();

    }

    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<?> removeUser(@PathVariable int userNo){

        // 서비스 --------------------------------------------------
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
        // ---------------------------------------------------------

        return ResponseEntity
                .noContent() // 204
                .build();
    }



}
