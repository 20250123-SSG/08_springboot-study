package com.younggalee.rest.section04.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

//개발하면서 동시에 문서작업가능하게됨
@Schema(description="회원 DTO")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserDto {
    @Schema(description = "회원번호")
    private int no;


    // 각 필드에 대한 validation
    @Schema(description = "아이디", example = "test01")
    @NotNull(message="아이디는 반드시 입력되어야 합니다.")
    @NotBlank(message ="아이디는 공백을 수 없습니다.")
    private String id;

    @Schema(description = "비밀번호", example = "password123!")
    @NotNull(message="비밀번호는 반드시 입력되어야합니다.")
    @Size(min=4, max=20, message="비밀번호는 4~20자 이어야 됩니다.")
    private String password;

    @Schema(description = "이름", example = "홍길동")
    @NotBlank(message = "이름은 반드시 입력되어야 합니다.")
    @Size(min=2, message = "이름은 2글자 이상이어야 됩니다.")
    private String name;

    private LocalDateTime createdDt;
}
