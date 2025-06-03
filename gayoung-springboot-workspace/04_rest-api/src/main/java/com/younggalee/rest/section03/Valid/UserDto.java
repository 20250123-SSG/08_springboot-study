package com.younggalee.rest.section03.Valid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserDto {
    private int no;


    // 각 필드에 대한 validation
    @NotNull(message="아이디는 반드시 입력되어야 합니다.")
    @NotBlank(message ="아이디는 공백을 수 없습니다.")
    private String id;


    @NotNull(message="비밀번호는 반드시 입력되어야합니다.")
    @Size(min=4, max=20, message="비밀번호는 4~20자 이어야 됩니다.")
    private String password;

    @NotBlank(message = "이름은 반드시 입력되어야 합니다.")
    @Size(min=2, message = "이름은 2글자 이상이어야 됩니다.")
    private String name;

    private LocalDateTime createdDt;
}
