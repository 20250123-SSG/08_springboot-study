package com.podoseee.rest.section04.swagger;

import com.podoseee.rest.section03.valid.ModifyGroup;
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

@Tag(name = "API 등록", description = "")

@RestController
@RequestMapping("/swagger")
public class SwaggerRestController {

    private List<UserDto> users;

    public SwaggerRestController(){
        users = new ArrayList<>();
        users.add(new UserDto(1, "user01", "pass01", "이마크", LocalDateTime.now()));
        users.add(new UserDto(2, "user02", "pass02", "나재민", LocalDateTime.now()));
        users.add(new UserDto(3, "user03", "pass03", "이제노", LocalDateTime.now()));
    }

    @Operation(summary = "회원 목록 조회", description = "회원 목록을 조회하는 기능입니다.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
            @ApiResponse(responseCode = "00", description = "조회 실패", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))
    })
    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers() {
        if(users.isEmpty()){
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }

        return ResponseEntity.ok(
                ResponseMessage.builder()
                        .status(200)
                        .message("회원 목록 조회 성공")
                        .results(Map.of("users", users))
                        .build()
        );
    }

    @Operation(summary = "회원 상세 조회", description = "회원 번호에 따른 회원 상세 정보를 조회하는 기능입니다.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
            @ApiResponse(responseCode = "00", description = "조회 실패", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class))),
            @ApiResponse(responseCode = "03", description = "경로 변수 오류", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))
    })
    @Parameter(name = "userNo", required = true, description = "조회할 회원 번호")
    @GetMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> findUserByNo(@PathVariable int userNo){
        UserDto foundedUser = users.stream()
                .filter(user -> user.getNo() == userNo)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("회원 정보를 찾을 수 없습니다."));

        return ResponseEntity.ok(
                ResponseMessage.builder()
                        .status(200)
                        .message("회원 상세 조회 성공")
                        .results(Map.of("user", foundedUser))
                        .build()
        );
    }

    @PutMapping("/users/{userNo}")
    public ResponseEntity<Void> modifyUser(@PathVariable int userNo,
                                           @Validated(ModifyGroup.class) @RequestBody UserDto modifyInfo){
        UserDto foundedUser = users.stream()
                .filter(user -> user.getNo() == userNo)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("회원 정보를 찾을 수 없습니다."));

        users.remove(foundedUser);

        modifyInfo.setNo(userNo);
        modifyInfo.setEnrollDate(foundedUser.getEnrollDate());
        users.add(modifyInfo);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<Void> removeUser(@PathVariable int userNo){
        UserDto foundedUser = users.stream()
                .filter(user -> user.getNo() == userNo)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("회원 정보를 찾을 수 없습니다."));

        users.remove(foundedUser);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseMessage> registUser(@Validated @RequestBody UserDto registInfo){
        int newUserNo = users.stream()
                .mapToInt(UserDto::getNo)
                .max()
                .orElse(0) + 1;

        registInfo.setNo(newUserNo);
        registInfo.setEnrollDate(LocalDateTime.now());

        users.add(registInfo);

        return ResponseEntity
                .created(URI.create("/swagger/users/" + registInfo.getNo()))
                .body(ResponseMessage.builder()
                        .status(201)
                        .message("회원 등록 성공")
                        .results(Map.of("user", registInfo))
                        .build());
    }
}
