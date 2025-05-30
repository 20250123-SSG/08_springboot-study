package com.ibe6.restapi.section04.swagger;

import io.swagger.v3.oas.annotations.Hidden;
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

    private List<UserDto> users;

    public SwaggerRestController() {
        users = new ArrayList<UserDto>();
        users.add(new UserDto(1, "user01", "pass01", "홍", LocalDateTime.now()));
        users.add(new UserDto(2, "user02", "pass02", "길", LocalDateTime.now()));
        users.add(new UserDto(3, "user03", "pass03", "동", LocalDateTime.now()));
    }


    @Operation(summary = "회원 목록 조회", description = "회원 목록을 조회하는 기능")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = ResponseMessage.class))
            ),
            @ApiResponse(
                    responseCode = "00",
                    description = "조회 실패",
                    content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class))
            )
    })
    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers() {
        if (users.isEmpty()) {
            throw new UserNotFoundException("회원들이없음");
        }

        return ResponseEntity
                .ok()
                .body(ResponseMessage.builder()
                        .status(200)
                        .message("회원들 조회 성공")
                        .results(Map.of("users", List.of(users)))
                        .build()
                );
    }


    @Operation(summary = "회원 조회", description = "회원 조회하는 기능")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = ResponseMessage.class))
            ),
            @ApiResponse(
                    responseCode = "00",
                    description = "조회 실패",
                    content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class))
            ),
            @ApiResponse(
                    responseCode = "03",
                    description = "경로 변수 오류",
                    content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class))
            )
    })
    @Parameter(name = "userNo", required = true, description = "조회할 회원 번호")
    @GetMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> findUserByUserNo(@PathVariable Integer userNo) {
        UserDto foundedUser = users.stream()
                .filter(u -> u.getNo() == userNo)
                .findFirst()
                .orElse(null);

        if (foundedUser == null) {
            throw new UserNotFoundException("회원 없음");
        }

        return ResponseEntity
                .ok()
                .body(ResponseMessage.builder()
                        .status(200)
                        .message("회원 조회 성공")
                        .results(Map.of("user", foundedUser))
                        .build()
                );
    }

    @PostMapping("/users")
    public ResponseEntity<?> registerUser(@Validated @RequestBody UserDto registerUser) {
        int nowUserNo = (users.get(users.size() - 1).getNo()) + 1;
        registerUser.setNo(nowUserNo);
        registerUser.setEnrollDate(LocalDateTime.now());
        users.add(registerUser);

        return ResponseEntity
                .created(URI.create("/valid/users/" + registerUser.getNo()))
                .build();
    }

    @PutMapping("/users/{userNo}")
    public ResponseEntity<?> modifyUser(@PathVariable Integer userNo,
                                        @Validated @RequestBody UserDto updateUser) {

        UserDto foundedUser = users.stream()
                .filter(u -> u.getNo() == userNo)
                .findFirst()
                .orElse(null);

        if (foundedUser == null) {
            throw new UserNotFoundException("회원 없음");
        }

        foundedUser.setId(updateUser.getId());
        foundedUser.setPwd(updateUser.getPwd());
        foundedUser.setName(updateUser.getName());

        return ResponseEntity
                .created(URI.create("/valid/users" + updateUser.getNo()))
                .build();
    }

    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userNo) {
        if (users.removeIf(u -> u.getNo() == userNo)) {
            return ResponseEntity.noContent().build();
        }
        throw new UserNotFoundException("회원 없음");
    }

}
