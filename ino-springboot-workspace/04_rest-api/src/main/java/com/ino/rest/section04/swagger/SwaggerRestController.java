package com.ino.rest.section04.swagger;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/swagger")
@RestController
public class SwaggerRestController {
    private List<UserDto> list;

    public SwaggerRestController() {
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

    @Operation(summary = "회원 목록 조회", description = "회원 목록 조회 기능")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ResponseMessage.class))), // body에 담기는 데이터 설명
            @ApiResponse(responseCode = "00", description = "조회 실패", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class))) // body에 담기는 데이터 설명
    })
    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers() { // 예외시 무조건 핸들링메소드로 던지기 때문에, 제네릭 사용x, 무조건 원하는 타입 리턴
        List<UserDto> foundedUsers = list;

        if (foundedUsers.isEmpty()) {
            throw new UserNotFoundException("Can't find user");
        }

        return ResponseEntity
                .ok()
                .body(ResponseMessage.builder()
                        .status(200)
                        .message("success to find user")
                        .results(Map.of("list", list))
                        .build());
    }

    @Operation(summary = "user detail", description = "user detail by userNo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ResponseMessage.class))), // body에 담기는 데이터 설명
            @ApiResponse(responseCode = "404", description = "조회 실패", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class))), // body에 담기는 데이터 설명
            @ApiResponse(responseCode = "03", description = "경로 변수 오류", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class))) // body에 담기는 데이터 설명
    })
    @Parameter(name = "userNo", required = true, description = "조회용 회원 번호")
    @GetMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> findUser(@PathVariable int userNo) {
        UserDto foundUser = null;
        foundUser = list.get(userNo);
        for (UserDto user : list) {
            if (user.getNo() == userNo) {
                foundUser = user;
            }
        }

        if (foundUser == null) {
            throw new UserNotFoundException("Can't find user");
        }

        return ResponseEntity
                .ok()
                .body(ResponseMessage.builder()
                        .status(200)
                        .message("success to find user")
                        .results(Map.of("user", foundUser))
                        .build());
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseMessage> registUser(@Validated @RequestBody UserDto registInfo) {
        // Service
        int lastUserNo = list.get(list.size()-1).getNo()+1;
        registInfo.setNo(lastUserNo);
        registInfo.setEnrollDate(LocalDateTime.now());
        list.add(registInfo);
        return ResponseEntity
                .created(URI.create("/valid/users/" + lastUserNo))
                .build();
    }

    @PutMapping("/users/{userNo}")
    public ResponseEntity<?> modUser(@PathVariable int userNo, @Validated @RequestBody UserDto modInfo){
        // 수정시 비밀번호, 이름만 필요하므로 validation 통과 못함 -> 요청별  다른 DTO 둬야함
        UserDto foundUser = null;
        foundUser = list.get(userNo);
        for (UserDto user : list) {
            if (user.getNo() == userNo) {
                foundUser = user;
            }
        }
        foundUser.setPwd(modInfo.getPwd());
        foundUser.setName(modInfo.getName());

        return ResponseEntity
                .created(URI.create("/valid/users/" + userNo))
                .build();

    }

    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<?> removeUser(@PathVariable int userNo){
        // 서비스
        UserDto foundUser = null;
        for (UserDto user : list) {
            if (user.getNo() == userNo) {
                foundUser = user;
            }
        }

        if(foundUser == null){
            throw new UserNotFoundException("cannot find user");
        }

        list.remove(foundUser);

        return ResponseEntity
                .noContent()
                .build();
    }
}
