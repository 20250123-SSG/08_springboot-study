package com.jjanggu.rest.section04.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Schema(description = "회원 DTO")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserDto {

    @Schema(description = "회원번호")
    private int no;

    @Schema(description = "아이디", example = "test01")
    @NotNull(message = "아이디는 반드시 입력되어야 합니다.")
    @NotBlank(message = "아이디는 공백일 수 없습니다.") // 사용 순서가 어떻게 되는거지? 선언 순서?
    private String id;

    @Schema(description = "비밀번호", example = "pass01")
    @NotNull(message = "비밀번호는 반드시 입력되어야 합니다.")
    @Size(min = 4, max = 20, message = "비밀번호는 4~20자 이어야 됩니다.")
    private String pwd;

    @Schema(description = "이름", example = "홍길동")
    @NotBlank(message = "이름은 반드시 입력되어야 합니다.")
    @Size(min = 2, message = "이름은 2글자 이상이어야 합니다.")
    private String name;

    @Schema(description = "가입일시")
    private LocalDateTime enrollDate;
}
